package com.server.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;

public class FirebaseInitialize {
  
  public void initialize(){
    final String keyName = "lamps-for-all-41ec3-firebase-adminsdk-o538x-253ab3d29b.json";
    final String jsonKey = this.getClass().getResource("/static/" + keyName).getPath();
    try {
      FileInputStream inputStream = new FileInputStream(jsonKey);
      FirebaseOptions options = new FirebaseOptions.Builder()
      .setDatabaseUrl("")
      .setProjectId("lamps-for-all-41ec3")
      .setConnectTimeout(10)
      .setCredentials(GoogleCredentials.fromStream(inputStream)).build();
      FirebaseApp.initializeApp(options);
      System.out.println("Firebase initialized...");
    } catch(Exception err) {
      System.out.println(err);
    }
  }
}