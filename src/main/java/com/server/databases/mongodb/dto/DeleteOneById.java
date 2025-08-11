package com.server.databases.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteOneById {
  
  @JsonProperty private String id;
  @JsonProperty private String collectionName;

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
