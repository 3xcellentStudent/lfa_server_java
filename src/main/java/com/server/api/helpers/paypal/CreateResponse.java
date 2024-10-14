package com.server.api.helpers.paypal;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class CreateResponse {

  public static ResponseEntity<String> toEntity(int status, String body){
    return ResponseEntity.status(status)
    .body(CreateResponse.stringify(status, body));
  }

  public static ResponseEntity<String> toEntity(int status, JSONObject body){
    return ResponseEntity.status(status)
    .body(CreateResponse.stringify(status, body));
  }
  
  public static String stringify(int status, JSONObject body){
    return CreateResponse.toJSON(status, body).toString();
  }

  public static String stringify(int status, String body){
    return CreateResponse.toJSON(status, body).toString();
  }

  public static JSONObject toJSON(int status, JSONObject body){
    return new JSONObject().put("status", status).put("data", body);
  }

  public static JSONObject toJSON(int status, String body){
    return new JSONObject().put("status", status).put("data", body);
  }
}
