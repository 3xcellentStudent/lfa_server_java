package com.server.heplers;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class PayPalHelper {
  public static ResponseEntity<JSONObject> resEntityJSON(int status, String data){
    return ResponseEntity.status(status).body(new JSONObject().put("data", data));
  }  
}
