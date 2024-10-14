package com.server.api.services.threads;

import org.json.JSONObject;

public class PayPalThreads implements Runnable {

  private JSONObject response;
  
  public PayPalThreads(JSONObject response){this.response = response;}

  @Override
  public void run(){
    try {
      // void createPdf = 
    } catch(Exception error){
      System.out.println(error.getMessage());
      error.printStackTrace();
      return;
    }
  }
}