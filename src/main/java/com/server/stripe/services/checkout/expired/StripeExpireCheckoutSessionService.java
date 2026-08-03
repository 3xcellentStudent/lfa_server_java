package com.server.stripe.services.checkout.expired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@Service
public class StripeExpireCheckoutSessionService {

  private Logger logger = LoggerFactory.getLogger(StripeExpireCheckoutSessionService.class);

  @Value("${stripe.routes.invoices.retrieve}")
  private String stripeRetrieveInvoiceEndpoint;
  @Value("${stripe.token.secret}")
  private String tokenSecret;
  @Value("${stripe.api.version}")
  private String stripeApiVersion;

  private HttpClient httpClient = HttpClient.newHttpClient();

  @Autowired
  private ObjectMapper objectMapper;

  private HttpRequest createRequest(String id){
    HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create(stripeRetrieveInvoiceEndpoint + "/" + id))
    .header("Authorization", "Bearer " + tokenSecret)
    .header("Stripe-Version", stripeApiVersion)
    .GET()
    .build();

    return request;
  }

  private StripeCheckoutExpiredDto mapping(String json){
    try {
      return objectMapper.readValue(json, StripeCheckoutExpiredDto.class);
    } catch(JsonProcessingException err){
      return null;
    }
  }

  public StripeCheckoutExpiredDto getOne(String id){
    try {
      HttpRequest request = createRequest(id);

      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      if(HttpStatus.OK.value() != response.statusCode()){
        return null;
      }

      return mapping(response.body());
    } catch(InterruptedException err){
      String message = "The request to retrieve stripe expired session was interrupted !";
      logger.error(message);
      return null;
    } catch(IOException err){
      String message = "Error occured while I/O retrieve stripe expired session request  !";
      logger.error(message);
      return null;
    }
  }

  public List<StripeCheckoutExpiredDto> getMulti(List<String> ids){
    List<CompletableFuture<StripeCheckoutExpiredDto>> futures = ids.stream().map(id -> {
      HttpRequest request = createRequest(id);

      return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(data -> data.body())
      .thenApply(jsonText -> mapping(jsonText));
    }).filter(Objects::nonNull).toList();

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    return futures.stream().map(future -> future.join()).toList();
  }
}
