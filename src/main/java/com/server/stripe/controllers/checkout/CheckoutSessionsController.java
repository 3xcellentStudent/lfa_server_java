package com.server.stripe.controllers.checkout;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionClientRequestDto;
// import com.server.stripe.helpers.services.thirdParty.RequestsToServices;
import com.server.stripe.services.checkout.StripeCheckoutCreateSessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  @Autowired
  private StripeCheckoutCreateSessionService createSessionService;

  @PostMapping("/create")
  public ResponseEntity<String> createCheckout(@Valid @RequestBody List<CheckoutCreateSessionClientRequestDto> body){
    // System.out.println(body.data.get(0).unitAmount);
    return createSessionService.create(body);
  }

  // public ResponseEntity<Object> save(@RequestBody String requestBodyString){
  //   try {
  //     HttpURLConnection mongodbConnection = CreateHttpUrlConnection
  //     .connect(mongodbSaveDataEndpoint, "POST", "application/json");
  //     mongodbConnection.setDoInput(true);
  //     mongodbConnection.setDoOutput(true);

  //     ResponseEntity<Object> mongodbResponse = requestsToServices.saveInDb(mongodbConnection, requestBodyString);

  //     mongodbConnection.disconnect();

  //     return ResponseEntity.ok().build();

  //     // if(mongodbResponse.getStatusCode().value() < 400){
  //     //   HttpURLConnection connectionCreatePdf = CreateHttpUrlConnection
  //     //   .connect(pdfCreateEndpoint, "POST", "application/json");
  //     //   connectionCreatePdf.setDoInput(true);
  //     //   connectionCreatePdf.setDoOutput(true);
  
  //     //   ResponseEntity<Object> pdfServiceResponse = requestsToServices.createPdf(connectionCreatePdf, requestBodyString);
  
  //     //   connectionCreatePdf.disconnect();

  //     //   return pdfServiceResponse;
  //     // } else {
  //     //   return ResponseEntity.internalServerError().body(mongodbResponse.getBody().toString());
  //     // }
  //   } catch (Exception error) {
  //     System.err.println(error.getMessage());
  //     error.printStackTrace();
  //     return ResponseEntity.internalServerError().body(error.getMessage());
  //   }
  // }

}
