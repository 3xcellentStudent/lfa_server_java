package com.server.stripe.helpers.services.thirdParty;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestsToServices {

  // private final String mongodbSaveDataEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/save";
  // private final String mailerSendEmailEndpoint = "http://localhost:5000/api/mailer/";
  private final String pdfCreateEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/save";

  private final Logger logger = org.slf4j.LoggerFactory.getLogger(RequestsToServices.class);

  private HttpClient httpClient = HttpClient.newHttpClient();

  // public ResponseEntity<String> createPdf(String requestBodyString){
  //   HttpRequest httpRequest = HttpRequest.newBuilder()
  //   .uri(java.net.URI.create(pdfCreateEndpoint))
  //   .header("Content-Type", "application/json")
  //   .POST(HttpRequest.BodyPublishers.ofString(requestBodyString))
  //   .build();

  //   try {
  //     String responseBodyString = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
  //     .thenApply(HttpResponse::body)
  //     .join();

  //     String responseString = responseReader.readStripeCheckoutSessionCompleted(responseBodyString);

  //     return ResponseEntity.ok(responseString);
  //   } catch (CompletionException error){
  //     String message = "Error occured while HTTP request to PDF service failed !";
  //     logger.error(message, error);
  //     return ResponseEntity.internalServerError()
  //     .body(message);
  //   }
  // }

}
