package com.server.coordinator.services.databases.mongodb.invoices.stripe;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.coordinator.services.pdf.PdfServiceApi;

@Service
public class StripeInvoicesServiceApi {

  private final String mongodbStripeInvoicesSaveEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/save";

  @Autowired
  private PdfServiceApi pdfServiceApi;

  public void saveInDatabase(String requestBodyString){
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(mongodbStripeInvoicesSaveEndpoint))
      .header("Content-Type", "application/json").timeout(Duration.ofSeconds(10))
      .PUT(BodyPublishers.ofString(requestBodyString)).build();
  
      HttpClient client = HttpClient.newHttpClient();
  
      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenAcceptAsync(response -> {
        // System.out.println(response.body());
        pdfServiceApi.createPdfDocument(response.body());
      })
      .exceptionallyAsync(error -> {
        error.printStackTrace();
        System.err.println(error.getMessage());
        // Do a new request to remote Mongo database to save result and then process it.
        return null;
      });
    } catch(URISyntaxException error){
      error.printStackTrace();
      System.err.println(error.getMessage());
    }
  }

}
