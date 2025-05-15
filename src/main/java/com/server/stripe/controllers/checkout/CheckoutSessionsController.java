package com.server.stripe.controllers.checkout;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.server.stripe.helpers.services.thirdParty.RequestsToServices;
import com.server.stripe.services.checkout.CheckoutCreateSessionService;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  // private final String mongodbSaveDataEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/save";
  // private final String pdfCreateEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/save";
  // private final String mailerSendEmailEndpoint = "http://localhost:5000/api/mailer/";

  @Autowired
  private CheckoutCreateSessionService createSessionService;
  // @Autowired
  // private RequestsToServices requestsToServices;
  
  @PostMapping("/create")
  public ResponseEntity<Object> createCheckout(@RequestBody String requestBodyString){
    // HttpURLConnection connection = CreateHttpUrlConnection
    // .connect(stripeCheckoutEndpoint, "POST", "application/x-www-form-urlencoded");

    // connection.setRequestProperty("Authorization", "Bearer " + tokenSecret);
    // connection.setRequestProperty("Stripe-Version", "2025-03-31.basil");
    // connection.setDoInput(true);
    // connection.setDoOutput(true);

    ResponseEntity<Object> response = createSessionService.create(requestBodyString);

    // connection.disconnect();

    return response;
  }

  // @PostMapping("/save")
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
