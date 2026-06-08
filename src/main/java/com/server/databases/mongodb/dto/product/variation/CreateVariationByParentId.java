package com.server.databases.mongodb.dto.product.variation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.firebase.database.annotations.NotNull;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.StockInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class CreateVariationByParentId {
  
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty @NotBlank private String variationName;
  @JsonProperty @NotBlank private String collectionName;
  @JsonProperty @Valid @NotNull private StockInfo stockInfo;

  public String getParentId(){
    return this.parentId;
  }
  
  public String getVariationName(){
    return this.variationName;
  }
  
  public String getCollectionName(){
    return this.collectionName;
  }
  
  public StockInfo getStockInfo(){
    return this.stockInfo;
  }
  
  CreateVariationByParentId(){}

}
