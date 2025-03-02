package com.server.payments.paypal.services;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.server.api.helpers.paypal.CreateResponse;
import com.server.api.helpers.paypal.PayPalRoutes;

public class PayPalCreateOrder {

	public static ResponseEntity<String> createOrder(String accessToken){

    try {
      URL url = new URI(PayPalRoutes.PAYPAL_CHECKOUT_ORDERS).toURL();
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("POST");
  
      httpConn.setRequestProperty("Content-Type", "application/json");
      httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
  
      httpConn.setDoOutput(true);
      OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
      writer.write("{ \"intent\": \"CAPTURE\", \"purchase_units\": [ { \"reference_id\": \"d9f80740-38f0-11e8-b467-0ed5f89f725b\", \"amount\": { \"currency_code\": \"CAD\", \"value\": \"100.00\" } } ], \"payment_source\": { \"paypal\": { \"experience_context\": { \"payment_method_preference\": \"IMMEDIATE_PAYMENT_REQUIRED\", \"brand_name\": \"EXAMPLE INC\", \"locale\": \"en-US\", \"landing_page\": \"LOGIN\", \"shipping_preference\": \"NO_SHIPPING\", \"user_action\": \"PAY_NOW\", \"return_url\": \"http://localhost:3000/payment/redirect/success\", \"cancel_url\": \"http://localhost:3000/payment/redirect/cancel\" } } } }");
      writer.flush();
      writer.close();
      httpConn.getOutputStream().close();
  
      InputStream responseStream = httpConn.getResponseCode() / 100 == 2
          ? httpConn.getInputStream()
          : httpConn.getErrorStream();
      Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
      String response = scanner.hasNext() ? scanner.next() : "";
      scanner.close();
      String orderId = new JSONObject(response).getString("id");
      System.out.println(String.format("Created order with ID: %s...", orderId));
      return CreateResponse.toEntity(200, response);
    } catch(Exception error){
      String message = "Server got error while creating a new order !";
      System.err.println(error.getMessage());
      error.printStackTrace();
      return CreateResponse.toEntity(500, message);
    }
	}
}
