package com.server.stripe.services.checkout.expired;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@Service
public class StripeExpireCheckoutSessionService {

  @Value("${stripe.routes.checkout.retrieve}")
  private String stripeRetrieveCkeckoutEndpoint;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

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

  public ResponseEntity<StripeCheckoutExpiredDto> getOne(String id){
      ResponseEntity<StripeCheckoutExpiredDto> httpResponse = restClient.get()
    .uri(URI.create(stripeRetrieveCkeckoutEndpoint + "/" + id))
    .header("Authorization", "Bearer " + tokenSecret)
    .header("Stripe-Version", stripeApiVersion)
    .retrieve()
    .toEntity(StripeCheckoutExpiredDto.class);;

    return httpResponse;
  }

  public List<StripeCheckoutExpiredDto> getMulti(List<String> ids){
    if(ids.size() == 0){
      return List.of();
    }

    List<CompletableFuture<StripeCheckoutExpiredDto>> futuresList = ids.stream()
    .map(id -> CompletableFuture.supplyAsync(() -> getOne(id).getBody())).toList();

    CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0])).join();

    return futuresList.stream().map(future -> future.join()).toList();
  }
}
