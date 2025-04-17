package com.server.stripe.helpers;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class CreateHttpUrlConnection {

  public static HttpURLConnection connect(String path, String httpMethodHeader, String contentTypeHeader){
    try {
      URL url = new URI(path).toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod(httpMethodHeader);
      connection.setRequestProperty("Content-Type", contentTypeHeader);

      return connection;
    } catch (Exception error) {
      System.out.println("Error while connection to " + path + "\nError message: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

}
