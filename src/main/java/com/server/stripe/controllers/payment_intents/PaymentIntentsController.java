package com.server.stripe.controllers.payment_intents;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.common.models.stripe.invoices.submodels.CheckoutSessionsDataModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.services.payment_intents.PaymentIntentsService;

@RestController
@RequestMapping("/api/stripe/payment/intents")
@CrossOrigin("*")
public class PaymentIntentsController {

  private final String token = "sk_test_51PxA1M04u95jjINWE9b9kEZlthF6QK20oiRoUYcs36vK3PeHyZtmQT6skXhLSZkX6nHiXGVaZ6V60PrDbKtrnFNw00vNOnvitG";
  
  @Autowired
  private PaymentIntentsService paymentIntentsCreateService;
  @Autowired
  private ObjectMapper objectMapper;
  
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      String path = "https://api.stripe.com/v1/payment_intents";
      URL url = new URI(path).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      connection.setRequestProperty("Authorization", "Bearer " + token);
      connection.setRequestProperty("Stripe-Version", "2025-03-31.basil");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      String responseString = paymentIntentsCreateService.create(connection, requestBodyString);

      connection.disconnect();

      // ResponseEntity<Object> response = objectMapper

      return ResponseEntity.ok(responseString);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PostMapping("/update/send-email")
  public ResponseEntity<Object> update(@RequestBody String requestBodyString){
    // System.out.println(requestBodyString);
    try {
      System.out.println(requestBodyString);
      StripeCheckoutSessionsModel requestBodyObject = objectMapper
      .readValue(requestBodyString, StripeCheckoutSessionsModel.class);
      System.out.println("requestBodyObject: " + requestBodyObject);

      CheckoutSessionsDataModel dataObject = requestBodyObject.getData();

      String path = "https://api.stripe.com/v1/payment_intents/" + dataObject.getPaymentIntentId();
      URL url = new URI(path).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      connection.setRequestProperty("Authorization", "Bearer " + token);
      connection.setRequestProperty("Stripe-Version", "2025-03-31.basil");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      // System.out.println(dataObject);
      System.out.println(dataObject.getCustomerDetails());

      String responseString = paymentIntentsCreateService.updateAndSendEmail(connection, dataObject.getCustomerDetails().email, requestBodyString);

      connection.disconnect();

      return ResponseEntity.ok(responseString);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

}
