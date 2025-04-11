package com.server.stripe.controllers.checkout;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

  private final String stripeCheckoutPath = "https://api.stripe.com/v1/checkout/sessions";
  private final String mongodbSaveDataPath = "http://localhost:5000/api/mongodb/invoices/stripe";
  private final String mailerSendEmailPath = "http://localhost:5000/api/mailer/";

  @Autowired
  private Environment env;
  @Autowired
  private CheckoutCreateSession createSessionService;

  private HttpURLConnection createConnection(String path, String httpMethodHeader, String contentTypeHeader){
    try {
      URL url = new URI(path).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod(httpMethodHeader);
      connection.setRequestProperty("Content-Type", contentTypeHeader);

      return connection;
    } catch (Exception error) {
      System.out.println("Error while connection to " + path + "\nError message: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }
  
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    String tokenSecret = env.getProperty("stripe.token.secret");
    try {
      // String path = "https://api.stripe.com/v1/checkout/sessions";
      // URL url = new URI(path).toURL();
      // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      HttpURLConnection connection = createConnection(stripeCheckoutPath, "POST", "application/x-www-form-urlencoded");

      // connection.setRequestMethod("POST");
      // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Authorization", "Bearer " + tokenSecret);
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

  @PostMapping("save")
  public ResponseEntity<Object> save(@RequestBody String requestBodyString){
    try {
      System.out.println(requestBodyString);
      HttpURLConnection connectionSaveInDb = createConnection(mongodbSaveDataPath, "POST", "application/json");
      connectionSaveInDb.setDoInput(true);
      connectionSaveInDb.setDoOutput(true);

      // ResponseEntity<Object> response = createSessionService.save(connectionSaveInDb, requestBodyString);
      createSessionService.save(connectionSaveInDb, requestBodyString);

      connectionSaveInDb.disconnect();

      HttpURLConnection connectionSendEmail = createConnection(mailerSendEmailPath, "POST", "application/json");
      connectionSendEmail.setDoInput(true);
      connectionSendEmail.setDoOutput(true);

      createSessionService.sendEmail(connectionSendEmail, requestBodyString);

      connectionSendEmail.disconnect();

      return ResponseEntity.ok("Payment saved in database and sent email letter to customer !");
    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

}
