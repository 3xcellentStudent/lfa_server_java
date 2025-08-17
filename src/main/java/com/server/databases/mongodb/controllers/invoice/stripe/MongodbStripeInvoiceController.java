package com.server.databases.mongodb.controllers.invoice.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.GetManyById;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.invoices.stripe.InvoicesStripeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mongodb/invoice/stripe")
@CrossOrigin("*")
public class MongodbStripeInvoiceController {

  @Autowired
  private InvoicesStripeService invoicesService;
  @Autowired
  private MongoDbMainService mainService;

  @Value("${mongodb.collections.invoice}")
  private String collectionName;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody StripeCheckoutSessionsModel body){
    ResponseEntity<Object> savedPaymentObject = invoicesService.createOne(body);

    return savedPaymentObject;
  }

  // @PutMapping("/update")
  // public ResponseEntity<Object> update(@RequestBody StripeCheckoutSessionsModel body){
  //   ResponseEntity<Object> savedPaymentObject = invoicesService.updateOne(body);

  //   return savedPaymentObject;
  // }

  @GetMapping("/get")
  public ResponseEntity<Object> findManyById(@Valid @RequestBody GetManyById body){
    ResponseEntity<Object> savedPaymentObject = invoicesService.findManyById(body);

    return savedPaymentObject;
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteManyById(@Valid @RequestBody DeleteManyById body){
    ResponseEntity<Object> savedPaymentObject = invoicesService.deleteManyById(body);

    return savedPaymentObject;
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return mainService.clearCollection(StripeCheckoutSessionsModel.class, collectionName);
  }

}
