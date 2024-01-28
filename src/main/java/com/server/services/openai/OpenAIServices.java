package com.server.services.openai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServices {
  private String apiKey = "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";
  private String encodedKey = Base64.getEncoder().encodeToString(apiKey.getBytes());
  private String URL = "https://api.openai.com/v1/completions";

  public String sendMessage(String message){
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    System.out.println(jsonObject);
    jsonArray.put(new JSONObject("role", "system"));
    jsonArray.put(new JSONObject("content", message));
    jsonObject.put("model", "davinci-002");
    jsonObject.put("messages", jsonArray);
    HttpRequest request = HttpRequest.newBuilder()
    .timeout(Duration.ofSeconds(3))
    .header("Content-Type", "application/json")
    .header("Authorization", "Bearer " + apiKey)
    .header("Accept", "application/json")
    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
    .uri(URI.create(URL))
    .build();
    System.out.println(jsonObject);
    try {
      HttpResponse<String> response = HttpClient.newBuilder()
      .build().send(request, BodyHandlers.ofString());
      System.out.println(response.body());
      return "";
    } catch(Exception err){
      System.err.println(err);;
      return "";
    }
  }
}