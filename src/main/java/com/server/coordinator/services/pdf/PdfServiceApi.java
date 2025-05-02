package com.server.coordinator.services.pdf;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.io.IOException; // import java.io.IOException for handling IOException
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException; // import java.util.concurrent.CompletionException for handling CompletionException

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PdfServiceApi {
  
  private final String pdfCreateDocEndpoint = "http://localhost:5000/api/pdf/create";
  // amazonq-ignore-next-line
  private final HttpClient client = HttpClient.newHttpClient();
  private static final Logger logger = LoggerFactory.getLogger(PdfServiceApi.class);

  public CompletableFuture<Void> createPdfDocument(String requestBodyString){

    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(pdfCreateDocEndpoint))
      // amazonq-ignore-next-line
      .header("Content-Type", "application/json").timeout(Duration.ofSeconds(5))
      .POST(BodyPublishers.ofString(requestBodyString)).build();

      return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenAcceptAsync(response -> {
        logger.info("PDF document created successfully");
      })
      .exceptionallyAsync(error -> {
        if(error instanceof CompletionException && error.getCause() instanceof IOException){
          logger.error("IOException occurred during HTTP request: {}", error.getCause().getMessage());
        } else if(error instanceof CompletionException && error.getCause() instanceof InterruptedException){
          logger.error("InterruptedException occurred during HTTP request: {}", error.getCause().getMessage());
          Thread.currentThread().interrupt();
        } else {
          logger.error("An error occurred during HTTP request", error);
        }
        return null;
      });
    } catch(URISyntaxException error){
      logger.error("URISyntaxException occurred", error);
      return CompletableFuture.failedFuture(error);
    }
  }

}
