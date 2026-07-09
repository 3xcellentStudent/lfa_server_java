package com.server.stripe.services.checkout.create;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.variation.ProductVariationService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.create.request.StripeCreateCheckoutSessionDto;

@Service
public class StripeCreateCheckoutSessionService {
  @Value("${stripe.routes.checkout.create_session}")
  private String stripeCreateCheckoutEndpoint;
  @Value("${stripe.routes.checkout.return_url}")
  private String stripeCheckoutReturnUrl;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  // @Autowired
  // private MongoDbMainService mongoDbMainService;
  @Autowired
  private ProductVariationService productVariationService;

  private Logger logger = LoggerFactory.getLogger(StripeCreateCheckoutSessionService.class);
  private HttpClient httpClient = HttpClient.newHttpClient();
  
  private final String encodingType = "UTF-8";
  private final String stockAmountAvailableKey = "stockInfo.stockAmountAvailable";
  private final String stockAmountReservedKey = "stockInfo.stockAmountReserved";

  // public ResponseEntity<Object> create(List<CheckoutCreateSessionClientRequestDto> body){
  public ResponseEntity<Object> create(List<CheckoutCreateSessionClientRequestDto> cart, List<ProductVariationModel> validatedArray){
    try {
      String returnUrl = URLEncoder.encode(stripeCheckoutReturnUrl, encodingType);

      productVariationService.bulkOpsInventoryUpdate(cart, validatedArray);
      // List<StripeCreateCheckoutSessionDto> afterDtoArray = body.stream()
      // .map(entity -> {
      //   ProductVariationModel variation = mongoDbMainService
      //   .findById(entity.productId(), ProductVariationModel.class, entity.collectionName());

      //   if(variation == null){
      //     return null;
      //   } else {
      //     logger.info("Product variation document with ID: " + entity.productId() + " was found !");
  
          // Integer stockAmountAvailable = variation.getStockInfo().getStockAmountAvailable() - entity.quantity();
          // Integer stockAmountReserved = variation.getStockInfo().getStockAmountReserved() + entity.quantity();
  
      //     // Updating database inventory.
      //     updateDatabase(variation.getId(), variation.getCollectionName(), stockAmountAvailable, stockAmountReserved);
  
      //     return new StripeCreateCheckoutSessionDto(entity, variation);
      //   }
      // })
      // .filter(entity -> entity != null).toList();

      String stringRequestBody = createRequest(validatedArray, returnUrl);
      
      ResponseEntity<Object> response = sendRequest(stringRequestBody);

      return response;
    } catch(UnsupportedEncodingException ex){
      String message = "The named encoding type " + "\"" + encodingType + "\"" + " is not supported !";
      logger.error(message, ex);
      throw new RuntimeException(message, ex);
    }
  }

  // private String createRequest(List<StripeCreateCheckoutSessionDto>dataArray, String returnUrl){
  private String createRequest(List<ProductVariationModel> dataArray, String returnUrl){
    
    StringBuilder requestBody = new StringBuilder();
    
    Instant expireTime = Instant.now().plus(30, ChronoUnit.MINUTES);
    
    requestBody.append("payment_method_types[]=card");
    requestBody.append("&mode=payment");
    requestBody.append("&ui_mode=embedded");
    requestBody.append("&invoice_creation[enabled]=true");
    requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
    requestBody.append("&return_url=").append(returnUrl);
    requestBody.append("&expires_at=").append(expireTime.getEpochSecond());
    
    for(int i = 0; i < dataArray.size(); i++){
      ProductVariationModel entity = dataArray.get(i);

      requestBody.append("&line_items[" + i + "][price_data][currency]=" + entity.getStockInfo().getCurrency());
      requestBody.append("&line_items[" + i + "][price_data][product_data][name]=" + entity.getVariationName());
      requestBody.append("&line_items[" + i + "][price_data][unit_amount]=" + entity.getStockInfo().getPriceInCents());
      requestBody.append("&line_items[" + i + "][quantity]=" + entity.getStockInfo().getStockAmountAvailable());
    }

    return requestBody.toString();
  }

  private ResponseEntity<Object> sendRequest(String stringRequestBody){
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(stripeCreateCheckoutEndpoint))
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

  // private ResponseEntity<Object> updateDatabase(String id,  String collectionName, Integer stockAmountAvailable, Integer stockAmountReserved){
  //   Update update = new Update();
  //   update.set(stockAmountAvailableKey, stockAmountAvailable);
  //   update.set(stockAmountReservedKey, stockAmountReserved);

  //   return mongoDbMainService.updateOneById(id, collectionName, update, ProductVariationModel.class);
  // }

  public StripeCreateCheckoutSessionService(){}

}
