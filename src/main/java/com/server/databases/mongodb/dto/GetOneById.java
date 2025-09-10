package com.server.databases.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class GetOneById {
  
  @JsonProperty @NotBlank private String id;
  @JsonProperty @NotBlank @Pattern(regexp = ".*-.*", message = "The field must contain '-'") private String collectionName;

  public String getId() {
    return id;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public GetOneById(){}

  public GetOneById(GetOneById body){
    this.id = body.getId();
    this.collectionName = body.getCollectionName();
  }

  public GetOneById(String id, String collectionName){
    this.id = id;
    this.collectionName = collectionName;
  }

}
