package com.server.coordinator.services.pdf;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;

import org.springframework.stereotype.Service;

@Service
public class PdfServiceApi {
  
  private final String pdfCreateDocEndpoint = "http://localhost:5000/api/pdf/create";

  public void createPdfDocument(String requestBodyString){
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(pdfCreateDocEndpoint))
      .header("Content-Type", "application/json").timeout(Duration.ofSeconds(10))
      .POST(BodyPublishers.ofString(requestBodyString)).build();

      HttpClient client = HttpClient.newHttpClient();

      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenAcceptAsync(response -> response.body())
      .exceptionallyAsync(error -> {
        error.printStackTrace();
        System.err.println(error.getMessage());
        return null;
      });
    } catch(URISyntaxException error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      // return ResponseEntity.internalServerError().body("Invalid url address.");
    }
  }

}
