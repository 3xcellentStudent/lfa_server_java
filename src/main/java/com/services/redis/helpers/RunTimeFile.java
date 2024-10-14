package com.services.redis.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunTimeFile {

  public static boolean run(String filePath) {

    try {
      ProcessBuilder processBuilder = new ProcessBuilder(filePath);
      processBuilder.redirectErrorStream(true);

      Process process = processBuilder.start();

      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while((line = reader.readLine()) != null) System.out.println(line);

      int exitCode = process.waitFor();
      System.out.println("Batch file executed with exit code: " + exitCode);
      
      return true;
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return false;
    }
  }
}
