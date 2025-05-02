package com.server.coordinator.services.mailer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.util.concurrent.CompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;

public class MailerServiceApi {

  private final HttpClient client = HttpClient.newHttpClient();
  
  private final Logger logger = LoggerFactory.getLogger(MailerServiceApi.class);

  public void send(@RequestBody String emailContent){
    System.out.println("Email Conent: " + emailContent);
    HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("http://localhost:5000/api/mailer/send"))
    .POST(BodyPublishers.ofString(emailContent))
    .header("", "")
    .timeout(Duration.ofSeconds(5))
    .build();

    client.sendAsync(request, null)
    .thenAcceptAsync(response -> {
      if(response.statusCode() == 200){
        // I should create endpoint for updating information status about current order in MongoDb that client that client got email notification with invoice pdf file
        logger.info("Email notification sent successfully");
      } else {
        // I'm not sure that I need this part of if/else block, because if operation is not successful then I will return status code >= 500 or >= 400
        logger.error("Email notification sending failed");
      }
    })
    .exceptionallyAsync(error -> {
      if(error instanceof CompletionException && error.getCause() instanceof IOException){
        logger.error("IOException occurred during HTTP request: ", error.getCause().getMessage());
      } else if(error instanceof CompletionException && error.getCause() instanceof InterruptedException){
        logger.error("InterruptedException occurred during HTTP request: ", error.getCause().getMessage());
        Thread.currentThread().interrupt();
      } else {
        error.printStackTrace();
        logger.error(error.getMessage());
      }
      return null;
    });
  }

}
