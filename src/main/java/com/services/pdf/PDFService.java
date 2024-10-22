package com.services.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;

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
import com.services.pdf.helpers.json.Address;
import com.services.pdf.helpers.json.CaptureResponseObject;
import com.services.pdf.helpers.json.Payer;
import com.services.pdf.services.Cells;
import com.services.pdf.services.Tables;

public class PDFService {

  public void create(CaptureResponseObject data){
    String fileName = "HelloWorld_" + System.currentTimeMillis() + ".pdf";

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try(Document document = new Document();){
      final PdfWriter instance = PdfWriter
      .getInstance(document, outputStream);

      metadata(instance);

      document.open();

      document.newPage();
      instance.setPageEmpty(false);

      mainInfoTable(data, document);
      
      billToInfoTable(data, document);

      descriptionFieldsTable(data, document);

      uploadPdfFile(outputStream.toByteArray(), fileName);
    } catch(DocumentException error){
      System.err.println(error.getMessage());
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

  private void mainInfoTable(CaptureResponseObject data, Document document){
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

  private void billToInfoTable(CaptureResponseObject data, Document document){
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

  private void descriptionFieldsTable(CaptureResponseObject data, Document document){
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

  private void uploadPdfFile(byte[] fileBytes, String fileName){
    try {
      URL url = new URI("").toURL();
      
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/octet-stream");
      connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

      connection.connect();

      OutputStream outputStream = connection.getOutputStream();
      

    } catch(Exception error){
      error.printStackTrace();
    }
  }
}