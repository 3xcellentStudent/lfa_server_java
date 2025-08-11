package com.server.databases.mongodb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetManyById {
  
  @JsonProperty private List<String> id;
  @JsonProperty private String collectionName;

  public List<String> getId() {
    return id;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public GetManyById(){}

}
