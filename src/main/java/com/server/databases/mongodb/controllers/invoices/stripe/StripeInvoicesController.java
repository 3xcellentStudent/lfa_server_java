package com.server.databases.mongodb.controllers.invoices.stripe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.services.invoices.stripe.InvoicesStripeService;
import com.server.pdf.models.CaptureResponseObject;

@RestController
@RequestMapping("/api/mongodb/invoices/stripe")
@CrossOrigin("*")
public class StripeInvoicesController {

  @Autowired
  private InvoicesStripeService invoicesService;

  @PostMapping("/save/completed")
  public ResponseEntity<Object> saveCompleted(@RequestBody String requestBodyString){
    ResponseEntity<Object> savedPaymentObject = invoicesService.createOne(requestBodyString);

    return savedPaymentObject;
  }

  @PostMapping("/find")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    ResponseEntity<Object> savedPaymentObject = invoicesService.findAllById(id);

    return savedPaymentObject;
  }

}
