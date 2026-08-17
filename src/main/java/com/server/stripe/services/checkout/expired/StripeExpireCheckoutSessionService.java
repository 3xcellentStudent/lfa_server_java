package com.server.stripe.services.checkout.expired;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@Service
public class StripeExpireCheckoutSessionService {

  // private Logger logger = LoggerFactory.getLogger(StripeExpireCheckoutSessionService.class);

  @Value("${stripe.routes.checkout.retrieve}")
  private String stripeRetrieveCkeckoutEndpoint;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  // private HttpClient httpClient = HttpClient.newHttpClient();

  private final RestClient restClient;

  public StripeExpireCheckoutSessionService(){
    HttpClient httpClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(2))
    .build();

    JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
    requestFactory.setReadTimeout(Duration.ofSeconds(5));

    this.restClient = RestClient.builder()
    .requestFactory(requestFactory)
    .build();
  }

  // private ResponseEntity<StripeCheckoutExpiredDto> createRequest(String id){
    // HttpRequest request = HttpRequest.newBuilder()
    // .uri(URI.create(stripeRetrieveInvoiceEndpoint + "/" + id))
    // .header("Authorization", "Bearer " + tokenSecret)
    // .header("Stripe-Version", stripeApiVersion)
    // .GET()
    // .build();

    // return request;
  // }

  // private StripeCheckoutExpiredDto mapping(String json){
  //   try {
  //     return objectMapper.readValue(json, StripeCheckoutExpiredDto.class);
  //   } catch(JsonProcessingException err){
  //     return null;
  //   }
  // }

  public ResponseEntity<StripeCheckoutExpiredDto> getOne(String id){
    // try {
      ResponseEntity<StripeCheckoutExpiredDto> httpResponse = restClient.get()
    .uri(URI.create(stripeRetrieveCkeckoutEndpoint + "/" + id))
    .header("Authorization", "Bearer " + tokenSecret)
    .header("Stripe-Version", stripeApiVersion)
    .retrieve()
    .toEntity(StripeCheckoutExpiredDto.class);;

    return httpResponse;
      // HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      // if(HttpStatus.OK.value() != response.statusCode()){
        // return null;
      // }

      // return mapping(response.body());
    // } catch(InterruptedException err){
    //   String message = "The request to retrieve stripe expired session was interrupted !";
    //   logger.error(message);
    //   return null;
    // } catch(IOException err){
    //   String message = "Error occured while I/O retrieve stripe expired session request  !";
    //   logger.error(message);
    //   return null;
    // }
  }

  public List<StripeCheckoutExpiredDto> getMulti(List<String> ids){
    if(ids.size() == 0){
      return List.of();
    }

    // List<CompletableFuture<StripeCheckoutExpiredDto>> futures = ids.stream().map(id -> {
    //   ResponseEntity<StripeCheckoutExpiredDto> request = getOne(id);

    //   return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    //   .thenApply(data -> data.body())
    //   .thenApply(jsonText -> mapping(jsonText));
    // }).filter(Objects::nonNull).toList();

    List<CompletableFuture<StripeCheckoutExpiredDto>> futuresList = ids.stream()
    .map(id -> CompletableFuture.supplyAsync(() -> getOne(id).getBody())).toList();

    CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0])).join();

    return futuresList.stream().map(future -> future.join()).toList();
  }
}
