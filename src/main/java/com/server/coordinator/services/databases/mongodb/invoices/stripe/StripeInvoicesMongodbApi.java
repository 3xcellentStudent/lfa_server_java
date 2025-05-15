package com.server.coordinator.services.databases.mongodb.invoices.stripe;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dto.coordinator.AfterCreationStripeInvoiceDto;
import com.common.models.stripe.invoices.StripeCheckoutSessionsWrapperModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.coordinator.services.pdf.PdfServiceApi;

@Service
public class StripeInvoicesMongodbApi {

  private final String mongodbStripeInvoicesSaveEndpoint = "http://localhost:5000/api/mongodb/invoices/stripe/create";

  private Logger logger = LoggerFactory.getLogger(StripeInvoicesMongodbApi.class);

  @Autowired
  private PdfServiceApi pdfServiceApi;
  @Autowired
  private ObjectMapper objectMapper;

  public void saveInDatabase(String requestBodyString){
    try {
      HttpRequest request = HttpRequest.newBuilder().uri(new URI(mongodbStripeInvoicesSaveEndpoint))
      .header("Content-Type", "application/json").timeout(Duration.ofSeconds(10))
      .POST(BodyPublishers.ofString(requestBodyString)).build();
  
      HttpClient client = HttpClient.newHttpClient();
  
      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenAcceptAsync(response -> fulfilled(response))
      .exceptionallyAsync(error -> rejected(error));
    } catch(URISyntaxException error){
      logger.error("Invalid URI !", error);
    }
  }

    private void fulfilled(HttpResponse<String> response){
      try {
        if(response.statusCode() == 200){
          StripeCheckoutSessionsWrapperModel stripeCheckoutSessionsWrapperModel = objectMapper
          .readValue(response.body(), StripeCheckoutSessionsWrapperModel.class);
          AfterCreationStripeInvoiceDto stripeInvoiceDto = new AfterCreationStripeInvoiceDto(stripeCheckoutSessionsWrapperModel);
          logger.info("Invoice data have been successfully created !");
          pdfServiceApi.createPdfDocument(stripeInvoiceDto);
        } else {
          logger.warn("Invoice data creation failed !");
        }
      } catch (JsonProcessingException error) {
        logger.error("An error occurred during processing (parsing, generating) JSON content !", error);
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
