package com.server.databases.mongodb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class DeleteManyById {
  
  @JsonProperty @NotBlank private List<String> id;
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty @NotBlank private String collectionName;

  DeleteManyById(){}

  DeleteManyById(DeleteManyById requestBody){
    this.id = requestBody.id;
    this.parentId = requestBody.parentId;
    this.collectionName = requestBody.collectionName;
  }

  DeleteManyById(List<String> id, String parentId, String collectionName){
    this.id = id;
    this.parentId = parentId;
    this.collectionName = collectionName;
  }

  public List<String> getId() {
    return id;
  }

  public String getParentId() {
    return parentId;
  }

  public String getCollectionName() {
    return collectionName;
  }

}
