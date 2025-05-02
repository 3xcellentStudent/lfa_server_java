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

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.pdf.models.CreatePdfDocumentDto;
// import com.server.pdf.models.CaptureResponseDto;
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
  public ResponseEntity<String> createPdfFile(@RequestBody String requestBodyString){
    try {
      StripeCheckoutSessionsModel stripeCheckoutSessionsObject = objectMapper.readValue(requestBodyString, StripeCheckoutSessionsModel.class);

      CreatePdfDocumentDto dto = new CreatePdfDocumentDto(stripeCheckoutSessionsObject.getData().object);
      ResponseEntity<String> responseBytes = pdfMainService.create(dto);

      return responseBytes;
    } catch(IOException error){
      error.printStackTrace();
      String errorCustomMessage = "IOException occurred during HTTP request";
      logger.error(errorCustomMessage + ": " + error.getMessage());
      return ResponseEntity.internalServerError().body(errorCustomMessage);
    }
  }

  // private String extractFileName(String contentDisposition) {
  //   String[] parts = contentDisposition.split(";");
  //   for (String part : parts) {
  //     if (part.trim().startsWith("filename=")) {
  //       return part.split("=")[1].trim().replaceAll("\"", "");
  //     }
  //   }
  //   return null;
  // }
}
