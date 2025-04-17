package com.server.stripe.helpers.services.thirdParty;

import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.stripe.helpers.CreateHttpUrlConnection;
import com.server.stripe.helpers.services.ResponseReader;

@Service
public class RequestsToServices {

  private final String mailerSendEmailEndpoint = "http://localhost:5000/api/mailer/";

  public ResponseEntity<Object> sendEmail(HttpURLConnection connection, String requestBodyString){
    try {
      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBodyString.getBytes();
      outputStream.write(bytes);
      outputStream.close();

      String responseString = ResponseReader.read(connection);

      return ResponseEntity.ok(responseString);
    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }

  public ResponseEntity<Object> createPdf(HttpURLConnection connection, String requestBodyString){
    try {
      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBodyString.getBytes();
      outputStream.write(bytes);
      outputStream.close();

      String responseString = ResponseReader.read(connection);

      if(connection.getResponseCode() == 200){
        HttpURLConnection connectionSendEmail = CreateHttpUrlConnection
        .connect(mailerSendEmailEndpoint, "POST", "application/json");
        connectionSendEmail.setDoInput(true);
        connectionSendEmail.setDoOutput(true);
  
        ResponseEntity<Object> emailServiceResponse = sendEmail(connectionSendEmail, requestBodyString);
  
        connectionSendEmail.disconnect();

        return emailServiceResponse;
      } else {
        return ResponseEntity.badRequest().body(responseString);
      }

    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body(error.getMessage());
    }
  }

  public ResponseEntity<Object> saveInDb(HttpURLConnection connection, String requestBodyString){
    try {
      OutputStream outputStream = connection.getOutputStream();
      byte[] bytes = requestBodyString.getBytes();
      outputStream.write(bytes);
      outputStream.close();

      String responseString = ResponseReader.read(connection);

      return ResponseEntity.ok(responseString);
    } catch (Exception error) {
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }

}
