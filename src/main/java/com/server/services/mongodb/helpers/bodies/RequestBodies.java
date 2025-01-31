package com.server.services.mongodb.helpers.bodies;

import java.util.List;

public class RequestBodies {
  
  public static class UpdateOneById {
    public String id;
    public List<String> fields;
    public List<String> newData;

    public UpdateOneById(){}

    public UpdateOneById(UpdateOneById requestBody){
      this.id = requestBody.id;
      this.fields = requestBody.fields;
      this.newData = requestBody.newData;
    }
  }

}
