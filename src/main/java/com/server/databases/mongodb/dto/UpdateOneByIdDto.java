package com.server.databases.mongodb.dto;

public class UpdateOneByIdDto {
  private String id;
  private String field;
  private Object newData;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Object getNewData() {
    return newData;
  }

  public void setNewData(Object newData) {
    this.newData = newData;
  }

  public UpdateOneByIdDto(){}

  public UpdateOneByIdDto(UpdateOneByIdDto requestBody){
    this.id = requestBody.id;
    this.field = requestBody.field;
    this.newData = requestBody.newData;
  }

  public UpdateOneByIdDto(String id, String field, Object newData, String collectionName){
    this.id = id;
    this.field = field;
    this.newData = newData;
  }

}
