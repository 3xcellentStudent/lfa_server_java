package com.server.stripe.services.checkout;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionClientRequestDto;
import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionClientRequestDto.DataArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CheckoutCreateSessionService {
  @Value("${stripe.routes.checkout.create_session}")
  private String stripeCheckoutEndpoint;
  @Value("${stripe.routes.checkout.return_url}")
  private String stripeCheckoutReturnUrl;

  private final int unitAmountCoefficient = 100;

  @Autowired
  private ObjectMapper objectMapper;
  
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  private Logger logger = LoggerFactory.getLogger(CheckoutCreateSessionService.class);

  private HttpClient httpClient = HttpClient.newHttpClient();
  
  public CheckoutCreateSessionService(){}
  
  public ResponseEntity<String> create(String incomingBodyString){
    String encodingType = "UTF-8";
    
    try {
      CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.
      readValue(incomingBodyString, CheckoutCreateSessionClientRequestDto.class);

      List<CheckoutCreateSessionClientRequestDto.DataArray> dataArray = requestBodyObject.data;

      String returnUrl = URLEncoder.encode(stripeCheckoutReturnUrl, encodingType);
      String stringRequestBody = createRequestBody(dataArray, returnUrl);
      
      // https://vitruvi.com/cdn/shop/files/pdp_stone-diffuser_front_white_gallery_1_v9_image.png?v=1740101659&width=320

      // HttpRequest request = HttpRequest.newBuilder()
      // .uri(new URI(stripeCheckoutEndpoint))
      // .header("Authorization", "Bearer " + tokenSecret)
      // .header("Stripe-Version", "2025-03-31.basil")
      // .header("Content-Type", "application/x-www-form-urlencoded")
      // .POST(HttpRequest.BodyPublishers.ofString(stringRequestBody))
      // .build();

      // CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      // String responseString = response.thenApply(HttpResponse::body).join();

      // System.out.println(responseString);

      // return ResponseEntity.ok(responseString);

      ResponseEntity<String> response = request(stringRequestBody);

      return response;
    } catch(UnsupportedEncodingException error){
      String message = "The named encoding type " + "\"" + encodingType + "\"" + " is not supported !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    // } catch(URISyntaxException error){
    //   logger.error("Invalid URI syntax or parcing from string to URI !", error);
    //   return ResponseEntity.internalServerError().body(error.getMessage());
    } catch(JsonProcessingException error){
      String message = "Error occured while parsing request body !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }

  private String createRequestBody(List<DataArray> dataArray, String returnUrl){
    StringBuilder requestBody = new StringBuilder();

    requestBody.append("payment_method_types[]=card");
    requestBody.append("&mode=payment");
    requestBody.append("&ui_mode=embedded");
    requestBody.append("&invoice_creation[enabled]=true");
    requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
    requestBody.append("&return_url=").append(returnUrl);

    for(int i = 0; i < dataArray.size(); i++){
      DataArray object = dataArray.get(i);

      requestBody.append("&line_items[" + i + "][price_data][currency]=cad");
      requestBody.append("&line_items[" + i + "][price_data][product_data][name]=" + object.productName);
      requestBody.append("&line_items[" + i + "][price_data][unit_amount]=" + object.unitAmount * unitAmountCoefficient);
      requestBody.append("&line_items[" + i + "][quantity]=" + object.quantity);
    }

    return requestBody.toString();
  }

  private ResponseEntity<String> request(String stringRequestBody){
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeCheckoutEndpoint))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", stripeApiVersion)
      .header("Content-Type", "application/x-www-form-urlencoded")
      .POST(HttpRequest.BodyPublishers.ofString(stringRequestBody))
      .build();

      CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      String responseString = response.thenApply(HttpResponse::body).join();

      return ResponseEntity.ok(responseString);
    } catch (URISyntaxException error) {
      String message = "Given string could not be parsed as a URI reference !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }

}
