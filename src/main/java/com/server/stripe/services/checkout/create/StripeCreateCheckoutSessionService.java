package com.server.stripe.services.checkout.create;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.stripe.dto.checkout.create.StripeCreateCheckoutSessionDto;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.webhook.completed.object.CheckoutSessionObjectModel;

@Service
public class StripeCreateCheckoutSessionService {
  @Value("${stripe.routes.checkout.create_session}")
  private String stripeCheckoutEndpoint;
  @Value("${stripe.routes.checkout.return_url}")
  private String stripeCheckoutReturnUrl;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  @Autowired
  private MongoDbMainService mongoDbMainService;

  private Logger logger = LoggerFactory.getLogger(StripeCreateCheckoutSessionService.class);
  private HttpClient httpClient = HttpClient.newHttpClient();
  
  private final String encodingType = "UTF-8";
  private final String stockAmountAvailable = "stockAmountAvailable";
  private final String stockAmountReserved = "stockAmountReserved";

  public ResponseEntity<Object> create(List<CheckoutCreateSessionClientRequestDto> body){
    
    try {
      // CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.
      // readValue(body, CheckoutCreateSessionClientRequestDto.class);

      // List<CheckoutCreateSessionClientRequestDto.DataArray> dataArray = body.data;

      String returnUrl = URLEncoder.encode(stripeCheckoutReturnUrl, encodingType);

      List<StripeCreateCheckoutSessionDto> afterDtoArray = body.stream()
      .map(entity -> {
        ProductVariationModel productVariation = mongoDbMainService
        .findById(entity.productId, ProductVariationModel.class, entity.collectionName);
        // logger.error("Product variation document with ID: " + entity.productId + " was not found !");
        logger.info("Product variation document with ID: " + entity.productId + " was found !");

        if(productVariation == null){
          return null;
        }

        Map<String, Integer> amountStockMap = new HashMap<>();
        amountStockMap.put(stockAmountAvailable, productVariation.getStockInfo().stockAmountAvailable - entity.quantity);
        amountStockMap.put(stockAmountReserved, productVariation.getStockInfo().getStockAmountReserved() + entity.quantity);

        ResponseEntity<Object> response = updateDatabase(productVariation.getId(), amountStockMap, productVariation.getCollectionName());

        if(response.getStatusCode().isSameCodeAs(HttpStatus.OK)){
          return new StripeCreateCheckoutSessionDto(entity, productVariation);
        } else {
          return null;
        }
      })
      .filter(entity -> entity != null).toList();

      String stringRequestBody = createRequest(afterDtoArray, returnUrl);


      
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

      ResponseEntity<Object> response = sendRequest(stringRequestBody);

      return response;
    } catch(UnsupportedEncodingException error){
      String message = "The named encoding type " + "\"" + encodingType + "\"" + " is not supported !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    // } catch(URISyntaxException error){
    //   logger.error("Invalid URI syntax or parcing from string to URI !", error);
    //   return ResponseEntity.internalServerError().body(error.getMessage());
    // } catch(JsonProcessingException error){
    //   String message = "Error occured while parsing request body !";
    //   logger.error(message, error);
    //   return ResponseEntity.badRequest().body(message);
    }
  }

  private String createRequest(List<StripeCreateCheckoutSessionDto>dataArray, String returnUrl){
    StringBuilder requestBody = new StringBuilder();

    requestBody.append("payment_method_types[]=card");
    requestBody.append("&mode=payment");
    requestBody.append("&ui_mode=embedded");
    requestBody.append("&invoice_creation[enabled]=true");
    requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
    requestBody.append("&return_url=").append(returnUrl);

    for(int i = 0; i < dataArray.size(); i++){
      StripeCreateCheckoutSessionDto entity = dataArray.get(i);
      // System.out.println("Price in cents: " + entity.priceInCents);

      requestBody.append("&line_items[" + i + "][price_data][currency]=" + entity.currency);
      requestBody.append("&line_items[" + i + "][price_data][product_data][name]=" + entity.variationName);
      requestBody.append("&line_items[" + i + "][price_data][unit_amount]=" + entity.priceInCents * entity.quantity);
      requestBody.append("&line_items[" + i + "][quantity]=" + entity.quantity);
    }

    return requestBody.toString();
  }

  private ResponseEntity<Object> sendRequest(String stringRequestBody){
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeCheckoutEndpoint))
      .header("Authorization", "Bearer " + tokenSecret)
      .header("Stripe-Version", stripeApiVersion)
      .header("Content-Type", "application/x-www-form-urlencoded")
      .POST(HttpRequest.BodyPublishers.ofString(stringRequestBody))
      .build();

      CompletableFuture<HttpResponse<String>> httpResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      String response = httpResponse.thenApply(HttpResponse::body).join();

      System.out.println("Response: " + response);

      return ResponseEntity.ok(response);
    } catch (URISyntaxException error) {
      String message = "Given string could not be parsed as a URI reference !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }

  private ResponseEntity<Object> updateDatabase(String id, Map<String, Integer> newData, String collectionName){
    UpdateOneByIdDto updateOneByIdDto = new UpdateOneByIdDto(id, "", newData, collectionName);

    Update update = new Update();
    update.set("stockInfo." + stockAmountAvailable, newData.get(stockAmountReserved));
    update.set("stockInfo." + stockAmountReserved, newData.get(stockAmountReserved));

    return mongoDbMainService.updateOneById(updateOneByIdDto, update, CheckoutSessionObjectModel.class);
  }

  public StripeCreateCheckoutSessionService(){}

}
