package com.server.stripe.services.checkout.expired;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.server.stripe.dto.checkout.create.request.StripeCreateCheckoutSessionDto;

@Service
public class StripeExpireCheckoutSessionService {
  
  @Value("${stripe.routes.invoices.retrieve}")
  private String stripeRetrieveInvoiceEndpoint;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  private HttpClient httpClient = HttpClient.newHttpClient();

  public ResponseEntity<Object> sendRequest(String invoiceId){
    System.out.println("URI: " + stripeRetrieveInvoiceEndpoint + "/" + invoiceId);
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeRetrieveInvoiceEndpoint + "/" + invoiceId))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", stripeApiVersion)
      // .header("Content-Type", "application/x-www-form-urlencoded")
      // .POST(HttpRequest.BodyPublishers.ofString(stringRequestBody))
      .GET()
      .build();

      CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      String response = httpResponse.thenApply(HttpResponse::body).join();

      System.out.println("Response: " + response);

      return ResponseEntity.ok(response);
    } catch (URISyntaxException error) {
      String message = "Given string could not be parsed as a URI reference !";
      // logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }
}
