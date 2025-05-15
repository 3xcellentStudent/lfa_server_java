package com.server.pdf.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.pdf.dto.CreatePdfDocumentDto;
import com.server.pdf.services.PdfMainService;


@RestController
@RequestMapping("/api/pdf")
@CrossOrigin("*")
public class PdfController {

  private final Logger logger = LoggerFactory.getLogger(PdfController.class);
  
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PdfMainService pdfMainService;

  @PostMapping("/create")
  public ResponseEntity<Object> createPdfFile(@RequestBody String requestBodyString){
    try {
      CreatePdfDocumentDto dto = objectMapper.readValue(requestBodyString, CreatePdfDocumentDto.class);
      
      ResponseEntity<Object> response = pdfMainService.create(dto);

      return response;
    } catch(IOException error){
      String message = "I/O exception occurred during HTTP request !";
      logger.error(message + ": " + error.getCause().getMessage());
      return ResponseEntity.internalServerError().body(message);
    }
  }
}
