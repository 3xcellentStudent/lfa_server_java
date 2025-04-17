package com.server.stripe.helpers.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.models.checkout.dtos.CheckoutCreateSessionServerResponseDto;
import com.server.stripe.models.checkout.dtos.CheckoutSessionsDataModel;

public class ResponseReader {

  public String response;

  public ResponseReader(){}

  // public ResponseReader(HttpURLConnection connection){
  //   try {
  //     InputStream inputStream = connection.getInputStream();
  //     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
  //     StringBuilder responseStringData = new StringBuilder();
  //     String line;

  //     while((line = reader.readLine()) != null){
  //       responseStringData.append(line);
  //     }

  //     reader.close();
  //     inputStream.close();

  //     CheckoutSessionsDataModel stripeResponseModel = objectMapper
  //     .readValue(responseStringData.toString(), CheckoutSessionsDataModel.class);
  //     CheckoutCreateSessionServerResponseDto serverResponseDto = new CheckoutCreateSessionServerResponseDto(stripeResponseModel);

  //     String responseString = objectMapper.writeValueAsString(serverResponseDto);
  //     this.response = responseString;
  //   } catch(Exception error){
  //     error.printStackTrace();
  //     System.err.println(error.getMessage());
  //     this.response = error.getMessage();
  //   }
  // }

  public static String read(HttpURLConnection connection){
    ObjectMapper objectMapper = new ObjectMapper();

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

      CheckoutSessionsDataModel stripeResponseModel = objectMapper
      .readValue(responseStringData.toString(), CheckoutSessionsDataModel.class);
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
