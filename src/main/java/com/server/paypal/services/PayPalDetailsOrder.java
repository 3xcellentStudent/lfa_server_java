package com.server.paypal.services;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import com.server.api.helpers.paypal.PayPalRoutes;

public class PayPalDetailsOrder {

	public static void getDetailsOrder(String orderId, String token){
    String uri = PayPalRoutes.PAYPAL_CHECKOUT_ORDERS + String.format("/%s", orderId);
    try {
      URL url = new URI(uri).toURL();
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("GET");
  
      httpConn.setRequestProperty("Authorization", "Bearer " + token);
  
      InputStream responseStream = httpConn.getResponseCode() / 100 == 2
          ? httpConn.getInputStream()
          : httpConn.getErrorStream();
      Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
      String response = scanner.hasNext() ? scanner.next() : "";
      scanner.close();
      System.out.println(response);
      return;
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return;
    }
	}
}