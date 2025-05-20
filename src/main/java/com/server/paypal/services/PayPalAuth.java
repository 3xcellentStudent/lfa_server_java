package com.server.paypal.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

public class PayPalAuth {
  // private final String client_id = "ATHQwc_7vusceyesHb517p7px8cvZZiQttp0aJwEXovWdahlvn_dkkerAETa8LmZe-0aETwoWGSzPFKB";
  // private final String client_secret = "EJfGzL6oUru26ztZlq2vehmpVTvB2LGl6vJg2t5BjxNpChZfTxSqOeMtyf6G0n4aPraY78O-9Z0xeRzw";
  
  private final String client_id = "";
  private final String client_secret = "";
  
  private String body = "grant_type=client_credentials";
  private String URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

  public String authorization(){
    String auth = client_id + ":" + client_secret;
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
    HttpRequest request = HttpRequest.newBuilder()
    .timeout(Duration.ofSeconds(10))
    .header("Authorization", "Basic " + encodedAuth)
    .header("Content-Type", "application/x-www-form-urlencoded")
    .POST(HttpRequest.BodyPublishers.ofString(body))
    .uri(URI.create(URL))
    .build();
    try {
      String response = HttpClient.newBuilder()
      .build().send(request, BodyHandlers.ofString()).body();
      return response;
    } catch(Exception error){
      System.err.println("PayPal authorization error: " + error.getMessage());
      error.printStackTrace();
      return "Server error !";
    }
  }
}