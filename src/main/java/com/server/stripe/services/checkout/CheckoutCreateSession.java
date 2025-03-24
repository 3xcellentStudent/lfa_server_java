package com.server.stripe.services.checkout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.helpers.dto.checkout.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.helpers.dto.checkout.CheckoutCreateSessionServerResponseDto;
import com.server.stripe.helpers.dto.checkout.CheckoutCreateSessionStripeResponseDto;

@Service
public class CheckoutCreateSession {
  @Autowired
  private ObjectMapper objectMapper;
  
  public CheckoutCreateSession(){}
  
  public ResponseEntity<Object> createSession(HttpURLConnection connection, String requestBodyString){
    try {
      CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);
      StringBuilder requestBody = new StringBuilder();
      
      requestBody.append("payment_method_types[]=card");
      requestBody.append("&line_items[0][price_data][currency]=cad");
      requestBody.append("&line_items[0][price_data][product_data][name]=" + requestBodyObject.productName);
      requestBody.append("&line_items[0][price_data][unit_amount]=" + requestBodyObject.unitAmount);
      requestBody.append("&line_items[0][quantity]=" + requestBodyObject.quantity);
      requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      requestBody.append("&mode=payment");
      requestBody.append("&ui_mode=embedded");
      // requestBody.append("&success_url=").append(URLEncoder.encode("https://www.google.com/", "UTF-8"));
      requestBody.append("&return_url=").append(URLEncoder.encode("http://localhost:5000/api/stripe/checkout/sessions/capture", "UTF-8"));
      // requestBody.append("&cancel_url=").append(URLEncoder.encode("http://localhost:5000/api/stripe/checkout/sessions/cancel", "UTF-8"));

      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBody.toString().getBytes("utf-8");
      outputStream.write(bytes);
      outputStream.close();

      String response = readResponse(connection, requestBodyString);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  private String readResponse(HttpURLConnection connection, String requestBody){
    try {
      InputStream inputStream = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      StringBuilder responseStringData = new StringBuilder();
      String line;

      while((line = reader.readLine()) != null){
        responseStringData.append(line);
      }

      reader.close();
      inputStream.close();

      // System.out.println(responseStringData.toString());

      CheckoutCreateSessionStripeResponseDto stripeResponseDto = objectMapper
      .readValue(responseStringData.toString(), CheckoutCreateSessionStripeResponseDto.class);

      CheckoutCreateSessionServerResponseDto serverResponseDto = new CheckoutCreateSessionServerResponseDto(stripeResponseDto);

      String responseString = objectMapper.writeValueAsString(serverResponseDto);
      
      return responseString;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return error.getMessage();
    }
  }

}
