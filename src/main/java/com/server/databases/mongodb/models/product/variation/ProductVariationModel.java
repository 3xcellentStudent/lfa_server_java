package com.server.databases.mongodb.models.product.variation;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ProductVariationModel {
 
  private @Id String id;
  private @Indexed @NotBlank String parentId;
  private @Valid  StockInfo stockInfo;
  private @NotBlank String variationName;
  private VariationOptions variationOptions;
  private List<Image> image;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Image {
    private String media;
    private String src;
    private String srcset;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Descriptions {
    private String summary;
    private List<String> presentable;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StockInfo {
    private @Min(0) Integer amountMax = 0;
    private @Min(0) Integer amountAvailable = 0;
    private @Min(0) Integer amountReserved = 0;
    private @Min(0) @NotNull Long priceInCents = 0L;
    private @NotBlank(message = "Cannot be blank") String currency = "CAD";
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class VariationOptions {
    private String name = "";
    private String type = "";
    private String value = "";
  }

  public ProductVariationModel(CreateVariationByParentId body){
    this.parentId = body.parentId();
    this.stockInfo = body.stockInfo() != null ? body.stockInfo() : new StockInfo();
    this.variationOptions = new VariationOptions();
    this.variationName = body.variationName();
    this.image = new ArrayList<Image>();
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }
}
