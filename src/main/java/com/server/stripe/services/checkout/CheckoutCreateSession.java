package com.server.stripe.services.checkout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionClientRequestDto;
import com.common.models.stripe.invoices.submodels.CheckoutCreateSessionServerResponseDto;
import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CheckoutCreateSession {
  @Autowired
  private ObjectMapper objectMapper;
  
  public CheckoutCreateSession(){}
  
  public ResponseEntity<Object> create(HttpURLConnection connection, String requestBodyString){
    try {
      CheckoutCreateSessionClientRequestDto requestBodyObject = objectMapper.
      readValue(requestBodyString, CheckoutCreateSessionClientRequestDto.class);
      StringBuilder requestBody = new StringBuilder();
      
      requestBody.append("payment_method_types[]=card");
      requestBody.append("&line_items[0][price_data][currency]=cad");
      requestBody.append("&line_items[0][price_data][product_data][name]=" + requestBodyObject.productName);
      requestBody.append("&line_items[0][price_data][unit_amount]=" + requestBodyObject.unitAmount);
      requestBody.append("&line_items[0][quantity]=" + requestBodyObject.quantity);
      requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      requestBody.append("&mode=payment");
      requestBody.append("&ui_mode=embedded");
      requestBody.append("&invoice_creation[enabled]=true");
      requestBody.append("&shipping_address_collection[allowed_countries][]=CA");
      requestBody.append("&return_url=").append(URLEncoder.encode("https://miro.medium.com/v2/resize:fit:720/format:webp/0*A7MUqyCLvZDcHkfM.jpg", "UTF-8"));

      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBody.toString().getBytes("utf-8");
      outputStream.write(bytes);
      outputStream.close();

      String response = readResponse(connection);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  private String readResponse(HttpURLConnection connection){
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

      StripeCheckoutSessionsDataModel stripeResponseModel = objectMapper
      .readValue(responseStringData.toString(), StripeCheckoutSessionsDataModel.class);
      CheckoutCreateSessionServerResponseDto serverResponseDto = new CheckoutCreateSessionServerResponseDto(stripeResponseModel);

      String responseString = objectMapper.writeValueAsString(serverResponseDto);
      return responseString;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return error.getMessage();
    }
  }

}
