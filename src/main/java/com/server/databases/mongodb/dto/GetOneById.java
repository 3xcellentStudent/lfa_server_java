package com.server.databases.mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetOneById {
  
  @JsonProperty private String id;
  @JsonProperty private String collectionName;

  public String getId() {
    return id;
  }

  public String getCollectionName() {
    return collectionName;
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
