package com.server.services.paypal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.server.services.heplers.PayPalHelper;

public class PayPalAuth {
  // @Value("${PAYPAL_CLIENT_ID}")
  // private String client_id;
  private String client_id = "AVn0ULKoBMaRcvml-G9PxmNuWtPj8bDYJvtbyxHDHjyi6XuJFSYZN6B-B7ZKBEW4n2LlyjiFlwx9cBBt";
  private String client_secret = "EHsON8KCADYDTAW2B_2F6Yr8K2TfWmF-EBSFigd8l7nypz0Wk5Hzqap_5nJejQkNKoud9jFY8o8M98rx";
  private String body = "grant_type=client_credentials";
  private String URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

  public ResponseEntity<JSONObject> callAuth(){
    String auth = client_id + ":" + client_secret;
    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
    HttpRequest request = HttpRequest.newBuilder()
    .timeout(Duration.ofSeconds(3))
    .header("Authorization", "Basic " + encodedAuth)
    .header("Content-Type", "application/x-www-form-urlencoded")
    .POST(HttpRequest.BodyPublishers.ofString(body))
    .uri(URI.create(URL))
    .build();
    try {
      HttpResponse<String> response = HttpClient.newBuilder()
      .build().send(request, BodyHandlers.ofString());
      // System.out.println(response.body());
      return PayPalHelper.resEntityJSON(200, response.body());
    } catch(Exception err){
      return PayPalHelper.resEntityJSON(500, err.getMessage());
    }
  }
}


// <dependency>
// 			<groupId>com.paypal.sdk</groupId>
// 			<artifactId>rest-api-sdk</artifactId>
// 			<version>1.14.0</version>
// 		</dependency>