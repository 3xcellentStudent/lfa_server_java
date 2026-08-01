package com.server.databases.mongodb.dto.product;

import java.util.List;

import com.server.databases.mongodb.models.product.ProductParentModel.Descriptions;
import com.server.databases.mongodb.models.product.ProductParentModel.MediaContent;
import com.server.databases.mongodb.models.product.ProductParentModel.Specifications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNewProductDto(
  @NotBlank String productName,
  @NotNull Descriptions descriptions,
  @NotNull Specifications specifications,
  @NotNull List<MediaContent> mediaContent,
  @NotBlank String category
){
  // @JsonProperty private @NotBlank String productName;
  // @JsonProperty private @Valid @NotNull Descriptions descriptions;
  // @JsonProperty private @Valid @NotNull Specifications specifications;
  // @JsonProperty private @Valid @NotNull ArrayList<MediaContent> mediaContent;

  // public String getProductName(){
  //   return this.productName;
  // }

  // public Descriptions getDescriptions(){
  //   return this.descriptions;
  // }

  // public Specifications getSpecifications(){
  //   return this.specifications;
  // }

  // public ArrayList<MediaContent> getMediaContent(){
  //   return this.mediaContent;
  // }

  // public String getCollectionName(){
  //   return this.collectionName;
  // }
  
}
