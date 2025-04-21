package com.server.pdf.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.server.pdf.models.Address;
import com.server.pdf.models.CaptureResponseDto;
import com.server.pdf.models.Payer;
import com.server.pdf.services.components.Cells;
import com.server.pdf.services.components.Tables;

@Service
public class PdfMainService {

  public JSONObject create(CaptureResponseDto data){
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try(Document document = new Document();){
      final PdfWriter instance = PdfWriter.getInstance(document, outputStream);

      metadata(instance);

      document.open();

      document.newPage();
      instance.setPageEmpty(false);

      mainInfoTable(data, document);
      
      billToInfoTable(data, document);

      descriptionFieldsTable(data, document);

      document.close();

      return new JSONObject().put("status", HttpURLConnection.HTTP_OK).put("data", outputStream.toByteArray());
    } catch(DocumentException error){
      System.err.println(error.getMessage());
      return null;
      // return new JSONObject().put("status", HttpURLConnection.HTTP_BAD_REQUEST).put("data", outputStream.toByteArray());
    }
  }
  
  private void metadata(final PdfWriter instance){
    instance.getInfo().put(PdfName.TITLE, new PdfString("Invoice #"));
    instance.getInfo().put(PdfName.CREATOR, new PdfString("version " + Document.getVersion()));
    instance.getInfo().put(PdfName.PRODUCER, new PdfString("Created by OpenPDF"));
    instance.getInfo().put(PdfName.AUTHOR, new PdfString("Andrew Prokuda"));
    instance.getInfo().put(PdfName.SUBJECT, new PdfString("Invoice for purchased goods"));
    instance.getInfo().put(PdfName.KEYWORDS, new PdfString("invoice, payment, order, purchase"));
    instance.getInfo().put(PdfName.CREATIONDATE, new PdfString((new Date().toString())));

    System.out.println("Metadata created !...");
    return;
  }

  private void mainInfoTable(CaptureResponseDto data, Document document){
    Tables multiTable1 = new Tables(2, 100);

    String[] content = {"Company Name", "My name", "My email"};
    String[][] innerMainContent = {
      {"Invoce#", "Creation date", "Currency"},
      {data.orderId, data.create_time, data.currency_code}
    };

    for(int i = 0; i < content.length; i++){
      float[] array = {50f, 50f};
      PdfPTable tableRow = new Tables(2, array)
      .twoColums(innerMainContent[0][i], innerMainContent[1][i],
      PdfPCell.ALIGN_LEFT, new float[]{0, 0, 0, 0});
      PdfPCell cell = new PdfPCell(tableRow);
      cell.setBorder(0);

      document.add(multiTable1.twoColums(content[i], cell));
    }
  }

  private void billToInfoTable(CaptureResponseDto data, Document document){
    Address address = data.address;
    Payer payer = data.payer;
    String fullName = payer.first_name + " " + payer.second_name;
    String[][] content = {
      {
        "Customer name:", 
        "Customer email:", 
        "Street:", 
        "Apartment/Suite:", 
        "Province/Region:",
        "City/Town:"
      },
      {
        fullName, 
        payer.email_address, 
        address.address_line_1,
        address.address_line_2,
        address.admin_area_1,
        address.admin_area_2
      }
    };

    document.add(new Tables(1, 100f)
    .createHeader("Bill To:", 0));
    
    for(int i = 0; i < content[0].length; i++){
      if(i == 2){
        document.add(new Tables(1, 100f)
        .createHeader("Shipping Address:", 0));
      }
      PdfPTable table = new Tables(2, new float[]{35f, 65f}).createTable(Element.ALIGN_LEFT);
      PdfPCell cell = new Cells(content[1][i]).createCell(PdfPCell.ALIGN_LEFT);
      table.addCell(new Cells(content[0][i]).createCell(PdfPCell.ALIGN_LEFT));
      table.addCell(cell);
      document.add(table);
    }

    return;
  }

  private void descriptionFieldsTable(CaptureResponseDto data, Document document){
    String[] tableFields = {"Items", "Quantity", "Total", "Subtotal"};
    PdfPTable table = new Tables(4, new float[]{55f, 15f, 15f, 15f}).createTable();
    table.setSpacingBefore(30f);
    table.setWidthPercentage(100f);
    for(int i = 0; i < tableFields.length; i++){
      Font font = new Font(Font.HELVETICA, Font.DEFAULTSIZE, Font.BOLD);
      Phrase phrase = new Phrase(tableFields[i], font);
      table.addCell(new Cells(phrase).createCell(PdfPCell.ALIGN_LEFT));
    }
    document.add(table);
  }

}