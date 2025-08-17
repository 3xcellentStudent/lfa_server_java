package com.server.databases.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class UpdateOneByIdDto {
  
  @JsonProperty @NotBlank private String id;
  @JsonProperty @NotBlank private String field;
  @JsonProperty @NotBlank private Object newData;
  @JsonProperty @NotBlank private String collectionName;

  public String getId() {
    return id;
  }

  public String getField() {
    return field;
  }

  public Object getNewData() {
    return newData;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public UpdateOneByIdDto(){}

  public UpdateOneByIdDto(UpdateOneByIdDto body){
    this.id = body.id;
    this.field = body.field;
    this.newData = body.newData;
    this.collectionName = body.collectionName;
  }

  public UpdateOneByIdDto(String id, String field, Object newData, String collectionName){
    this.id = id;
    this.field = field;
    this.newData = newData;
    this.collectionName = collectionName;
  }

}
