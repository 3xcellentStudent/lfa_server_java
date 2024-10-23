package com.server.services.pdf.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.pdf.PDFService;
import com.server.services.pdf.models.CaptureResponseObject;

@RestController
@RequestMapping("/service-pdf")
public class PdfController {
  
  @PostMapping(value = "/create")
  public void createPdfFile(@RequestBody String body){
    JSONObject jsonBody = new JSONObject(body);
    JSONObject jsonResponse = new PDFService().create(new CaptureResponseObject(jsonBody));
    if(jsonResponse != null){
      byte[] fileBytes = (byte[]) jsonResponse.get("data");
      System.out.println(fileBytes.length);
      // If response is not null send mail to recipient and send pdf document to my server
      
    } else {
      // Send request from gcp to my server, saving data about error for creating pdf and send mail later. 
    }
    // return;
  }
}
