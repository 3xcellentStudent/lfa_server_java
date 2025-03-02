package com.resources.redis.services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.server.databases.redis.helpers.RunTimeFile;

public class PortChecker {

  private boolean isPortInUse(){
    String relativePath = "../files.bat/redis.start.bat";
    String filePath = this.getClass().getResource(relativePath).getPath();

    try (Socket socket = new Socket()) {
      SocketAddress socketAddress = new InetSocketAddress("localhost", 6379);
      socket.connect(socketAddress, 2000);
      System.out.println("Redis server is running !");
      socket.close();
      return true;
    } catch(IOException error){
      System.err.println("Redis server is not working. Starting Redis server...");
      if(RunTimeFile.run(filePath)) System.out.println("Redis server launched !");
      else System.out.println("Redis server is not launched !");
      return false;
    }
  }

  public static boolean checkPort(){return new PortChecker().isPortInUse();}
}
