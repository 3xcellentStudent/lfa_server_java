package com.server.services.firebase;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class FirebaseHelper {
  public static ResponseEntity<JSONObject> resEntityJSON(int status, Object data){
    return ResponseEntity.status(status).body(new JSONObject().put("data", data));
  }
}