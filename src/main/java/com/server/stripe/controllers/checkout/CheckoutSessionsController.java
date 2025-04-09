package com.server.stripe.controllers.checkout;

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

import com.server.stripe.services.checkout.CheckoutCreateSession;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  private final String token = "sk_test_51PxA1M04u95jjINWE9b9kEZlthF6QK20oiRoUYcs36vK3PeHyZtmQT6skXhLSZkX6nHiXGVaZ6V60PrDbKtrnFNw00vNOnvitG";

  @Autowired
  private CheckoutCreateSession createSessionService;
  
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      String path = "https://api.stripe.com/v1/checkout/sessions";
      URL url = new URI(path).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      connection.setRequestProperty("Authorization", "Bearer " + token);
      connection.setRequestProperty("Stripe-Version", "2025-03-31.basil");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      ResponseEntity<Object> response = createSessionService.create(connection, requestBodyString);

      connection.disconnect();

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  @PostMapping("/capture")
  public void capture(@RequestBody String requestBodyString){

    System.out.println("CAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTUREDCAPTURED");
    System.out.println(requestBodyString);
  }

  @PostMapping("/cancel")
  public void cancel(){
    System.out.println("CANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCELCANCEL");
  }

}
