package com.server.stripe.services.checkout.create;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
// import com.server.databases.mongodb.services.product.variation.ProductVariationService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.webhook.checkout.events.completed.object.StripeCheckoutCompletedDto;

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
  // @Autowired
  // private ProductVariationService productVariationService;

  private final RestClient restClient;

  public StripeCreateCheckoutSessionService(){
    HttpClient httpClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(2))
    .build();

    JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
    requestFactory.setReadTimeout(Duration.ofSeconds(5));

    this.restClient = RestClient.builder()
    .requestFactory(requestFactory)
    .build();
  }
  
  // private final String encodingType = "UTF-8";

  @Transactional
  public ResponseEntity<StripeCheckoutCompletedDto> create(List<CheckoutCreateSessionClientRequestDto> cart, List<ProductVariationModel> validatedArray){
    String stringRequestBody = createRequest(validatedArray);

    // productVariationService.bulkOpsInventoryUpdate(cart);
    
    ResponseEntity<StripeCheckoutCompletedDto> response = sendRequest(stringRequestBody);

    return response;

    // StripeCheckoutCompletedDto sessionDto = objectMapper.readValue(response, StripeCheckoutCompletedDto.class);
  }

  // private String createRequest(List<StripeCreateCheckoutSessionDto>dataArray, String returnUrl){
  private String createRequest(List<ProductVariationModel> validatedArray){
    
    StringBuilder requestBody = new StringBuilder();
    
    Instant expireTime = Instant.now().plus(30, ChronoUnit.MINUTES);
    
    requestBody.append("payment_method_types[]=card");
    requestBody.append("&mode=payment");
    requestBody.append("&ui_mode=embedded");
    requestBody.append("&invoice_creation[enabled]=true");
    requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
    requestBody.append("&return_url=").append(URI.create(stripeCheckoutReturnUrl));
    requestBody.append("&expires_at=").append(expireTime.getEpochSecond());
    
    for(int i = 0; i < validatedArray.size(); i++){
      ProductVariationModel entity = validatedArray.get(i);

      requestBody.append("&line_items[" + i + "][price_data][currency]=" + entity.getStockInfo().getCurrency());
      requestBody.append("&line_items[" + i + "][price_data][product_data][name]=" + entity.getVariationName());
      requestBody.append("&line_items[" + i + "][price_data][unit_amount]=" + entity.getStockInfo().getPriceInCents());
      requestBody.append("&line_items[" + i + "][quantity]=" + entity.getStockInfo().getStockAmountAvailable());
    }

    return requestBody.toString();
  }

  private ResponseEntity<StripeCheckoutCompletedDto> sendRequest(String body){
    return restClient.post()
    .uri(stripeCreateCheckoutEndpoint)
    .header("Authorization", "Bearer " + tokenSecret)
    .header("Stripe-Version", stripeApiVersion)
    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    .body(body)
    .retrieve()
    .toEntity(StripeCheckoutCompletedDto.class);

      // HttpRequest request = HttpRequest.newBuilder()
      // .uri(URI.create(stripeCreateCheckoutEndpoint))
      // .header("Authorization", "Bearer " + tokenSecret)
      // .header("Stripe-Version", stripeApiVersion)
      // .header("Content-Type", "application/x-www-form-urlencoded")
      // .POST(HttpRequest.BodyPublishers.ofString(stringRequestBody))
      // .build();

      // CompletableFuture<HttpResponse<String>> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

      // String response = future.thenApply(data -> data.body()).join();

      // return response;
  }

}
