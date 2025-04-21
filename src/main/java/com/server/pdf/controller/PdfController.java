package com.server.pdf.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mailer.MailerService;
import com.server.pdf.models.CaptureResponseDto;
import com.server.pdf.services.PdfMainService;
import com.server.pdf.services.http.HttpService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin("*")
public class PdfController {
  
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  PdfMainService mainService;
  // @PostMapping(value = "/create")
  // public void createPdfFile(@RequestBody String body){
  //   JSONObject jsonBody = new JSONObject(body);
  //   JSONObject jsonResponse = new PDFService().create(new CaptureResponseObject(jsonBody));
  //   if(jsonResponse != null){
  //     byte[] fileBytes = (byte[]) jsonResponse.get("data");
  //     System.out.println(fileBytes.length);
  //     String fileName = "invoice_" + System.currentTimeMillis() + ".pdf";
  //     HttpService.uploadPdfFile(fileBytes, fileName);
  //     MailerService.send("lamps.for.all00@gmail.com", fileBytes, fileName, "Invoice on Your email.", "Thank You for purchasing in our store !");
  //   } else {
  //     // Send request from gcp to my server, saving data about error for creating pdf and send mail later. 
  //   }
  // }

  @PostMapping("/write")
  // public String writePdfFile(HttpServletRequest request, @RequestHeader("Content-Disposition") String contentDisposition){
  public String writePdfFile(@RequestBody String requestBodyString){
    System.out.println(requestBodyString);
    try {
      StripeCheckoutSessionsModel stripeCheckoutSessionsObject = objectMapper.readValue(requestBodyString, StripeCheckoutSessionsModel.class);

      // mainService.create()

      return "File uploaded and saved to";
    } catch (IOException error) {
      error.printStackTrace();
      return "Failed to save file: " + error.getMessage();
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
