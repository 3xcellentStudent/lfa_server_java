package com.server.databases.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DeleteOneById {
  
  @JsonProperty @NotBlank private String id;
  @JsonProperty @NotBlank @Pattern(regexp = ".*-.*", message = "The field must contain '-'") private String collectionName;

  public DeleteOneById(){}

  public DeleteOneById(DeleteOneById body){
    this.id = body.id;
    this.collectionName = body.collectionName;
  }

  public DeleteOneById(String id, String collectionName){
    this.id = id;
    this.collectionName = collectionName;
  }

  public String getId(){
    return this.id;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

}
