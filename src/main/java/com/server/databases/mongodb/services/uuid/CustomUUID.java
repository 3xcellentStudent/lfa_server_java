package com.server.databases.mongodb.services.uuid;

import java.util.UUID;

public class CustomUUID {
  
  public static String fromString(String string){
    return UUID.nameUUIDFromBytes(string.getBytes()).toString();
  }

  public static String fromString(String[] strings){
    return UUID.nameUUIDFromBytes(String.join("-").getBytes()).toString();
  }

}
