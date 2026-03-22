package com.server.databases.mongodb.dto.product;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.models.product.ProductParentModel.Descriptions;
import com.server.databases.mongodb.models.product.ProductParentModel.MediaContent;
import com.server.databases.mongodb.models.product.ProductParentModel.Specifications;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateNewProduct {
  @JsonProperty private @NotBlank String productName;
  @JsonProperty private @Valid @NotNull Descriptions descriptions;
  @JsonProperty private @Valid @NotNull Specifications specifications;
  @JsonProperty private @Valid @NotNull ArrayList<MediaContent> mediaContent;
  @JsonProperty private @NotBlank String collectionName;

  public String getProductName(){
    return this.productName;
  }

  public Descriptions getDescriptions(){
    return this.descriptions;
  }

  public Specifications getSpecifications(){
    return this.specifications;
  }

  public ArrayList<MediaContent> getMediaContent(){
    return this.mediaContent;
  }

  public String getCollectionName(){
    return this.collectionName;
  }
  
}
