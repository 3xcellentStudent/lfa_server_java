package com.server.pdf.services;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.server.pdf.dto.CreatePdfDocumentDto;
import com.server.pdf.services.components.Cells;
import com.server.pdf.services.components.Tables;

@Service
public class PdfMainService {

  private final Logger logger = LoggerFactory.getLogger(PdfMainService.class);

  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> create(CreatePdfDocumentDto dto){
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try(Document document = new Document();){
      logger.info(String.format("Creating the PDF document with ID %s...", dto.invoiceId));
      final PdfWriter instance = PdfWriter.getInstance(document, outputStream);

      metadata(instance);

      document.open();
      document.newPage();

      instance.setPageEmpty(false);

      mainInfoTable(dto, document);
      
      billToInfoTable(dto, document);

      descriptionFieldsTable(dto, document);

      document.close();

      // Files.write(Path.of(String.format("/home/andrew/Desktop/newfile-%d.pdf", System.currentTimeMillis())), outputStream.toByteArray());
      logger.info(String.format("The PDF document with ID %s successfuly created !", dto.invoiceId));

      String fileName = String.format("newfile-%d.pdf", System.currentTimeMillis());

      Map<String, Object> responseBodyMap = new HashMap<>();
      responseBodyMap.put("fileName", fileName);
      responseBodyMap.put("fileBytes", outputStream.toByteArray());
      responseBodyMap.put("emailContent", dto);

      String responseBodyString = objectMapper.writeValueAsString(responseBodyMap);

      return ResponseEntity.ok()
      .header("Content-Type", "application/json")
      .body(responseBodyString);
    } catch(IOException error){
      logger.error("An error occurred during creating document !", error);
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }
  
  private void metadata(final PdfWriter instance){
    instance.getInfo().put(PdfName.TITLE, new PdfString("Invoice #"));
    instance.getInfo().put(PdfName.CREATOR, new PdfString("version " + Document.getVersion()));
    instance.getInfo().put(PdfName.PRODUCER, new PdfString("Created by OpenPDF"));
    instance.getInfo().put(PdfName.AUTHOR, new PdfString("My store name"));
    instance.getInfo().put(PdfName.SUBJECT, new PdfString("Invoice for purchased goods"));
    instance.getInfo().put(PdfName.KEYWORDS, new PdfString("invoice, payment, order, purchase"));
    instance.getInfo().put(PdfName.CREATIONDATE, new PdfString((new Date().toString())));

    return;
  }

  private void mainInfoTable(CreatePdfDocumentDto dto, Document document){
    Tables multiTable1 = new Tables(2, 100);

    String[] content = {"Company Name", "My name", "My email"};
    String[][] innerMainContent = {
      {"Invoce#", "Creation date", "Currency"},
      {dto.invoiceId, dto.created.toString(), dto.currency}
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

  private void billToInfoTable(CreatePdfDocumentDto dto, Document document){
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
        dto.customerName, 
        dto.customerEmail, 
        dto.address.line1,
        dto.address.line2,
        dto.address.state,
        dto.address.postalCode
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

  private void descriptionFieldsTable(CreatePdfDocumentDto dto, Document document){
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