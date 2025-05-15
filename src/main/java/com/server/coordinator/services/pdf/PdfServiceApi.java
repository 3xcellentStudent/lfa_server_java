package com.server.coordinator.services.pdf;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.io.IOException;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dto.coordinator.AfterCreationStripeInvoiceDto;
import com.server.coordinator.services.mailer.MailerServiceApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PdfServiceApi {
  
  private final String pdfCreateDocEndpoint = "http://localhost:5000/api/pdf/create";

  private final HttpClient client = HttpClient.newHttpClient();
  private static final Logger logger = LoggerFactory.getLogger(PdfServiceApi.class);

  @Autowired
  private MailerServiceApi mailerServiceApi;
  @Autowired
  private ObjectMapper objectMapper;

  public void createPdfDocument(AfterCreationStripeInvoiceDto stripeInvoiceDto){
    try {
      String requestDtoStringPdf = objectMapper.writeValueAsString(stripeInvoiceDto);

      HttpRequest request = HttpRequest.newBuilder().uri(new URI(pdfCreateDocEndpoint))
      .header("Content-Type", "application/json")
      .timeout(Duration.ofSeconds(10))
      .POST(BodyPublishers.ofString(requestDtoStringPdf)).build();

      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenAcceptAsync(response -> fulfilled(response))
      .exceptionallyAsync(error -> rejected(error));
    } catch(URISyntaxException error){
      logger.error("Invalid URI.", error);
    } catch(IOException error){
      logger.error("An error occurred during HTTP operations.", error);
    }
  }

  private void fulfilled(HttpResponse<String> response){
    if(response.statusCode() == 200){
      logger.info("PDF document created successfully");
      mailerServiceApi.send(response.body());
    } else {
      logger.error("PDF document creation failed");
    }
  }

  private Void rejected(Throwable error){
    if(error instanceof CompletionException && error.getCause() instanceof IOException){
      logger.error("IOException occurred during HTTP request: ", error.getCause().getMessage());
    } else if(error instanceof CompletionException && error.getCause() instanceof InterruptedException){
      logger.error("InterruptedException occurred during HTTP request: ", error.getCause().getMessage());
      Thread.currentThread().interrupt();
    } else {
      logger.error("An error occurred during HTTP request", error);
    }
    return null;
  }

}
