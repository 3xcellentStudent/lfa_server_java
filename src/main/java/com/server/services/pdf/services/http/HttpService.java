package com.server.services.pdf.services.http;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpService {
  
  public static void uploadPdfFile(byte[] fileBytes, String fileName){
    try {
      URL url = new URI("http://localhost:5000/files/write-pdf").toURL();
      
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/octet-stream");
      connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
      connection.connect();

      OutputStream outputStream = connection.getOutputStream();
      outputStream.write(fileBytes);
      outputStream.flush();
      outputStream.close();

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) System.out.println("File uploaded successfully.");
      else System.err.println("Failed to upload file. Response code: " + connection.getResponseCode());

      connection.disconnect();

    } catch(Exception error){
      error.printStackTrace();
      return;
    }
  }
}
