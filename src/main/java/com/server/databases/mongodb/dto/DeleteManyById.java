package com.server.databases.mongodb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class DeleteManyById {
  
  @JsonProperty @NotEmpty private List<String> id;
  @JsonProperty private String parentId;
  @JsonProperty @NotBlank @Pattern(regexp = ".*-.*", message = "The field must contain '-'") private String collectionName;

  DeleteManyById(){}

  DeleteManyById(DeleteManyById requestBody){
    this.id = requestBody.getId();
    this.parentId = requestBody.getParentId();
    this.collectionName = requestBody.getCollectionName();
  }

  DeleteManyById(List<String> id, String parentId, String collectionName){
    this.id = id;
    this.parentId = parentId;
    this.collectionName = collectionName;
  }

  public List<String> getId() {
    return this.id;
  }

  public String getParentId() {
    return this.parentId;
  }

  public String getCollectionName() {
    return this.collectionName;
  }

}
