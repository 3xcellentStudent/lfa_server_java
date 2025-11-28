package com.server.databases.mongodb.dto.product.variation;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class PostVariationByParentId {
  
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty private String variationName;
  @JsonProperty @NotBlank private String collectionName;

  PostVariationByParentId(){}

  public String getParentId(){
    return this.parentId;
  }

  public String getVariationName(){
    return this.variationName;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

}
