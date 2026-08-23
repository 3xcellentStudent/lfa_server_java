package com.server.databases.mongodb.dto.product;

import java.util.List;

import com.server.databases.mongodb.models.product.parent.ProductParentModel.Descriptions;
import com.server.databases.mongodb.models.product.parent.ProductParentModel.MediaContent;
import com.server.databases.mongodb.models.product.parent.ProductParentModel.Specifications;

import jakarta.validation.constraints.NotBlank;

public record CreateNewProductDto(
  @NotBlank String productName,
  Descriptions descriptions,
  Specifications specifications,
  List<MediaContent> mediaContent,
  @NotBlank String category
){}
