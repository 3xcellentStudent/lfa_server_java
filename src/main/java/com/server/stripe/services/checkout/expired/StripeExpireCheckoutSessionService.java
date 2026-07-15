package com.server.stripe.services.checkout.expired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.services.orders.OrdersService;

@Service
public class StripeExpireCheckoutSessionService {

  @Value("${stripe.routes.invoices.retrieve}")
  private String stripeRetrieveInvoiceEndpoint;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  private HttpClient httpClient = HttpClient.newHttpClient();

  @Autowired
  private OrdersService ordersService;

  public ResponseEntity<Object> sendRequestSync(String checkoutId){
    System.out.println("URI: " + stripeRetrieveInvoiceEndpoint + "/" + checkoutId);
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeRetrieveInvoiceEndpoint + "/" + checkoutId))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", stripeApiVersion)
      .GET()
      .build();

      // CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      // String response = httpResponse.thenApply(body -> body.body()).join();

      HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      return ResponseEntity.ok(httpResponse.body());

    } catch(IOException er){
      String message = "Failed to communicate with Stripe server (Network error or timeout) !";
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(message);
    } catch(InterruptedException er){
      String message = "The request processing was interrupted internally by the server to Stripe retrieve checkout session !";
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    } catch (URISyntaxException er) {
      String message = "Given string could not be parsed as a URI reference !";
      return ResponseEntity.badRequest().body(message);
    }
  }

  public ResponseEntity<Object> sendMultiRequestsAsync(String invoiceId){
    System.out.println("URI: " + stripeRetrieveInvoiceEndpoint + "/" + invoiceId);
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeRetrieveInvoiceEndpoint + "/" + invoiceId))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", stripeApiVersion)
      .GET()
      .build();

      // CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      // String response = httpResponse.thenApply(body -> body.body()).join();

      HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      return ResponseEntity.ok(httpResponse.body());

    } catch(IOException er){
      String message = "Failed to communicate with Stripe server (Network error or timeout) !";
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(message);
    } catch(InterruptedException er){
      String message = "The request processing was interrupted internally by the server to Stripe retrieve checkout session !";
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    } catch (URISyntaxException er) {
      String message = "Given string could not be parsed as a URI reference !";
      return ResponseEntity.badRequest().body(message);
    }
  }

  @Scheduled(fixedRate = 1800000)
  public void getAllOpenOrder(){
    List<MainOrderModel> matchedOrders = ordersService.getAllBySelector("status", List.of("open"));
    System.out.println("ORDERS:" + matchedOrders.size());
  }
}
