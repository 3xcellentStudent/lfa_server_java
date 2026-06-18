package com.server.databases.mongodb.dto.main;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DeleteManyFromArray {
  
  @JsonProperty @NotBlank private List<Integer> indexes;
  @JsonProperty @NotBlank private String selector;
  @JsonProperty @NotBlank private String id;
  @JsonProperty @NotBlank @Pattern(regexp = ".*-.*", message = "The field must contain '-'") private String collectionName;

  DeleteManyFromArray(){}

  DeleteManyFromArray(DeleteManyFromArray body){
    this.indexes = body.indexes;
    this.selector = body.selector;
    this.id = body.id;
    this.collectionName = body.collectionName;
  }

  DeleteManyFromArray(List<Integer> indexes, String selector, String id, String collectionName){
    this.indexes = indexes;
    this.selector = selector;
    this.id = id;
    this.collectionName = collectionName;
  }

  public String getId() {
    return this.id;
  }

  public List<Integer> getIndexes(){
    return this.indexes;
  }

  public String getSelector(){
    return this.selector;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

}
