package com.server.databases.mongodb.dto.product.variation;

import com.google.firebase.database.annotations.NotNull;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel.StockInfo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateVariationByParentId(@NotBlank String parentId, @NotBlank String variationName, @Valid @NotNull StockInfo stockInfo){}
