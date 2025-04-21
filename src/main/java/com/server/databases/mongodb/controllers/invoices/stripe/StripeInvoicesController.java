package com.server.databases.mongodb.controllers.invoices.stripe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.invoices.stripe.InvoicesStripeService;
// import com.server.pdf.models.CaptureResponseDto;

@RestController
@RequestMapping("/api/mongodb/invoices/stripe")
@CrossOrigin("*")
public class StripeInvoicesController {

  @Autowired
  private InvoicesStripeService invoicesService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody String requestBodyString){
    // ResponseEntity<Object> savedPaymentObject = invoicesService.createOne(requestBodyString);

    // return savedPaymentObject;
    System.out.println(requestBodyString);

    return ResponseEntity.ok(requestBodyString);
  }

  @PostMapping("/find")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    ResponseEntity<Object> savedPaymentObject = invoicesService.findAllById(id);

    return savedPaymentObject;
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = false) List<String> id){
    ResponseEntity<Object> savedPaymentObject = invoicesService.deleteAllById(id);

    return savedPaymentObject;
  }

  @GetMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return mainService.clearCollection(StripeCheckoutSessionsModel.class);
  }

}
