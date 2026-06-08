package com.server.databases.mongodb.dto.product.variation;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateVariationById {
    
  @JsonProperty @NotBlank private String id;
  @JsonProperty @NotBlank private String field;
  @JsonProperty private Object newData;
  @JsonProperty @NotBlank @Pattern(regexp = ".*-.*", message = "The field must contain '-'") private String collectionName;

  public String getId() {
    return this.id;
  }

  // public String getParentId(){
  //   return this.parentId;
  // }

  public String getField() {
    return this.field;
  }

  public Object getNewData() {
    return this.newData;
  }

  public String getCollectionName() {
    return this.collectionName;
  }

  public UpdateVariationById(){}

  public UpdateVariationById(UpdateVariationById body){
    this.id = body.getId();
    // this.parentId = body.getParentId();
    this.field = body.getField();
    this.newData = body.getNewData();
    this.collectionName = body.getCollectionName();
  }

}