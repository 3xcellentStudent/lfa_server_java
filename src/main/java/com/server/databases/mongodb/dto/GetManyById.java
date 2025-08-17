package com.server.databases.mongodb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class GetManyById {
  
  @JsonProperty private List<String> id;
  @JsonProperty @NotBlank private String collectionName;

  public List<String> getId() {
    return id;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public GetManyById(){}

}
