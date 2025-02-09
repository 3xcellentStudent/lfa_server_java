package com.server.services.mongodb.helpers.bodies;

import java.util.List;

public class RequestBodies {
  
  public static class UpdateOneById {
    public String id;
    public String field;
    public Object newData;

    public UpdateOneById(){}

    public UpdateOneById(UpdateOneById requestBody){
      this.id = requestBody.id;
      this.field = requestBody.field;
      this.newData = requestBody.newData;
    }
  }

}
