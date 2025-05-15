package com.server.stripe.services.checkout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionClientRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CheckoutCreateSessionService {
  private final String stripeCheckoutEndpoint = "https://api.stripe.com/v1/checkout/sessions";
  private final String stripeCheckoutReturnUrl = "https://miro.medium.com/v2/resize:fit:720/format:webp/0*A7MUqyCLvZDcHkfM.jpg";

  @Autowired
  private Environment env;
  @Autowired
  private ObjectMapper objectMapper;

  private Logger logger = LoggerFactory.getLogger(CheckoutCreateSessionService.class);

  private HttpClient httpClient = HttpClient.newHttpClient();
  
  public CheckoutCreateSessionService(){}
  
  public ResponseEntity<Object> create(String requestBodyString){
    String tokenSecret = env.getProperty("stripe.token.secret");
    
    try {
      CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.
      readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);

      StringBuilder requestBody = new StringBuilder();
      
      requestBody.append("payment_method_types[]=card");
      requestBody.append("&line_items[0][price_data][currency]=cad");
      requestBody.append("&line_items[0][price_data][product_data][name]=" + requestBodyObject.productName);
      requestBody.append("&line_items[0][price_data][unit_amount]=" + requestBodyObject.unitAmount);
      requestBody.append("&line_items[0][quantity]=" + requestBodyObject.quantity);
      requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      requestBody.append("&mode=payment");
      requestBody.append("&ui_mode=embedded");
      requestBody.append("&invoice_creation[enabled]=true");
      requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      requestBody.append("&return_url=").append(URLEncoder.encode(stripeCheckoutReturnUrl, "UTF-8"));

      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeCheckoutEndpoint))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", "2025-03-31.basil")
      .header("Content-Type", "application/x-www-form-urlencoded")
      .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
      .build();

      CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      String responseString = response.thenApply(HttpResponse::body).join();

      // CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.
      // readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);
      // StringBuilder requestBody = new StringBuilder();
      
      // requestBody.append("payment_method_types[]=card");
      // requestBody.append("&line_items[0][price_data][currency]=cad");
      // requestBody.append("&line_items[0][price_data][product_data][name]=" + requestBodyObject.productName);
      // requestBody.append("&line_items[0][price_data][unit_amount]=" + requestBodyObject.unitAmount);
      // requestBody.append("&line_items[0][quantity]=" + requestBodyObject.quantity);
      // requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      // requestBody.append("&mode=payment");
      // requestBody.append("&ui_mode=embedded");
      // requestBody.append("&invoice_creation[enabled]=true");
      // requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      // requestBody.append("&return_url=").append(URLEncoder.encode("https://miro.medium.com/v2/resize:fit:720/format:webp/0*A7MUqyCLvZDcHkfM.jpg", "UTF-8"));

      // OutputStream outputStream = connection.getOutputStream();
      // byte[] bytes = requestBody.toString().getBytes("utf-8");
      // outputStream.write(bytes);
      // outputStream.close();

      // String response = readResponse(connection);

      return ResponseEntity.ok(responseString);
    } catch(IOException error){
      String message = "Error occured while http I/O !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    } catch(URISyntaxException error){
      logger.error("Invalid URI syntax or parcing from string to URI !", error);
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

}
