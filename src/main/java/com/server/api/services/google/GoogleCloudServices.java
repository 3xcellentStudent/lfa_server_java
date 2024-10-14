package com.server.api.services.google;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class GoogleCloudServices {
  public void createPdf(){
    try {
      URI uri = URI.create("");
      HttpRequest request = HttpRequest.newBuilder()
      .uri(uri)
      .setHeader("Content-Type", "application/json")
      .POST(BodyPublishers.ofString(""))
      .build();
  
      HttpResponse response = HttpClient.newBuilder()
      .build().send(request, BodyHandlers.ofString());
    } catch(Exception error){
      System.err.println(
        "Error was received in \"GoogleCloudServices.java\": \n" + error.getMessage() + "\nWith stack trace: \n"
      );
      error.printStackTrace();
      // return null;
    }
  }
}
