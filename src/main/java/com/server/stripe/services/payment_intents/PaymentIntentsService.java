package com.server.stripe.services.payment_intents;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.models.checkout.classess.CheckoutCreateSessionClientRequestDto;

@Service
public class PaymentIntentsService {
  
  @Autowired
  private ObjectMapper objectMapper;

  public PaymentIntentsService(){}

  public String create(HttpURLConnection connection, String requestBodyString){
    try {
      CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper
      .readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);
      StringBuilder requestBody = new StringBuilder();
      
      // requestBody.append("payment_method_types[]=card");
      requestBody.append("&currency=cad");
      requestBody.append("&confirm=true");
      requestBody.append("&confirmation_method=automatic");
      requestBody.append("&capture_method=automatic");
      requestBody.append("&payment_method_data[type]=card");
      // requestBody.append("&receipt_email=");
      requestBody.append("&amount=" + requestBodyObject.unitAmount);
      requestBody.append("&automatic_payment_methods[enabled]=true");
      requestBody.append("&return_url=").append(URLEncoder.encode("http://localhost:5000/api/stripe/checkout/sessions/capture", "UTF-8"));

      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBody.toString().getBytes("utf-8");
      outputStream.write(bytes);
      outputStream.close();

      String response = readResponse(connection, requestBodyString);

      // return ResponseEntity.ok(response);
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      // return ResponseEntity.internalServerError().body(error.getMessage());
      return error.getMessage();
    }
  }

  public String updateAndSendEmail(HttpURLConnection connection, String email, String requestBodyString){
    try {
      // CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper
      // .readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);
      StringBuilder requestBody = new StringBuilder();
      
      requestBody.append("&receipt_email=" + email);

      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBody.toString().getBytes("utf-8");
      outputStream.write(bytes);
      outputStream.close();

      String response = readResponse(connection, requestBodyString);

      // return ResponseEntity.ok(response);
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      // return ResponseEntity.internalServerError().body(error.getMessage());
      return error.getMessage();
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

      System.out.println(responseStringData.toString());

      // CheckoutCreateSessionStripeResponseDto stripeResponseDto = objectMapper
      // .readValue(responseStringData.toString(), CheckoutCreateSessionStripeResponseDto.class);

      // CheckoutCreateSessionServerResponseDto serverResponseDto = new CheckoutCreateSessionServerResponseDto(stripeResponseDto);
      
      return responseStringData.toString();
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return error.getMessage();
    }
  }
}
