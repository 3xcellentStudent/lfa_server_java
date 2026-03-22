package com.server.coordinator.controller.checkout.stripe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.coordinator.services.databases.mongodb.invoices.stripe.StripeInvoicesMongodbApi;

@RestController
@RequestMapping("/api/coordinator/checkout/stripe")
public class StripeCheckoutMessageReceiver {

  @Autowired
  private StripeInvoicesMongodbApi stripeInvoicesServiceApi;
  
  @PostMapping("/paid")
  public ResponseEntity<Object> checkoutCompleted(@RequestBody String requestBodyString){
    stripeInvoicesServiceApi.saveInDatabase(requestBodyString);

    return ResponseEntity.ok().build();
  }
  
}
