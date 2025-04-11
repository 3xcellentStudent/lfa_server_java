package com.server.databases.mongodb.controllers.invoices.stripe;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.pdf.models.CaptureResponseObject;

@RestController
@RequestMapping("/api/mongodb/invoices/stripe")
@CrossOrigin("*")
public class InvoicesController {

  @PostMapping("save/completed")
  public void saveCompleted(@RequestBody String requestBodyString){
    System.out.println(requestBodyString);
  }

}
