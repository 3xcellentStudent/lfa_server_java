package com.server.databases.mongodb.helpers.bodies;

public class UpdateOneById {
  public String id;
  public String field;
  public Object newData;

  public UpdateOneById(){}

  public UpdateOneById(UpdateOneById requestBody){
    this.id = requestBody.id;
    this.field = requestBody.field;
    this.newData = requestBody.newData;
  }

  public UpdateOneById(String id, String field, Object newData){
    this.id = id;
    this.field = field;
    this.newData = newData;
  }
}
