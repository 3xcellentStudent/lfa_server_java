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
// import jakarta.validation.constraints.Pattern;
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

  @Id 
  private String id;
  
  @Indexed 
  @NotBlank 
  private String parentId;
  @Valid private StockInfo stockInfo;
  @NotBlank private String variationName;
  private VariationOptions variationOptions;
  private List<Image> image;
  // @Pattern(regexp = "^variation-.*$", message = "\"collection\" must contain \"variation\" and \"-\" !") 
  private String category;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Image {
    private String media;
    private String src;
    private String srcset;

    // public String getMedia(){
    //   return this.media;
    // }

    // public String getSrc(){
    //   return this.src;
    // }

    // public String getSrcset(){
    //   return this.srcset;
    // }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Descriptions {
    private String summary;
    private List<String> presentable;

    // public String getSummary(){
    //   return this.summary;
    // }

    // public String[] getPresentable(){
    //   return this.presentable;
    // }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StockInfo {
    @Min(0) private Integer stockAmountMax;
    @Min(0) private Integer stockAmountAvailable;
    @Min(0) private Integer stockAmountReserved;
    @Min(0) @NotNull private Long priceInCents;
    @NotBlank(message = "Cannot be blank") private String currency;

    // public Integer stockAmountMax(){
    //   return this.stockAmountMax;
    // }

    // public Integer getStockAmountAvailable(){
    //   return this.stockAmountAvailable;
    // }

    // public Integer getStockAmountReserved(){
    //   return this.stockAmountReserved;
    // }

    // public Long getPriceInCents(){
    //   return this.priceInCents;
    // }

    // public String getCurrency(){
    //   return this.currency;
    // }

    // public void setStockAmountMax(Integer newStockAmountMax){
    //   this.stockAmountMax = newStockAmountMax;
    // }

    // public void setStockAmountAvailable(Integer newStockAmountAvailable){
    //   this.stockAmountAvailable = newStockAmountAvailable;
    // }

    // public void setStockAmountReserved(Integer newStockAmountReserved){
    //   this.stockAmountReserved = newStockAmountReserved;
    // }

    // public void setPriceInCents(Long newPriceInCents){
    //   this.priceInCents = newPriceInCents;
    // }

    // public void setCurrency(String newCurrency){
    //   this.currency = newCurrency;
    // }

    // public StockInfo(){
    //   this.stockAmountMax = 0;
    //   this.stockAmountAvailable = 0;
    //   this.stockAmountReserved = 0;
    //   this.priceInCents = 0L;
    //   this.currency = "CAD";
    // }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class VariationOptions {
    private String name = "";
    private String type = "";
    private String value = "";

    // public String getName(){
    //   return this.name;
    // }

    // public String getType(){
    //   return this.type;
    // }

    // public String getValue(){
    //   return this.value;
    // }
  }

  // @Data
  // @NoArgsConstructor
  // @AllArgsConstructor
  // public static class Item {
  //   private String value;
  //   private String fill;
  //   private String stroke;
  //   private Boolean stockStatus;
  //   private Integer mediaIndex;

    // public String getValue(){
    //   return this.value;
    // }

    // public String getFill(){
    //   return this.fill;
    // }

    // public String getStroke(){
    //   return this.stroke;
    // }

    // public Boolean getStockStatus(){
    //   return this.stockStatus;
    // }

    // public Integer getMediaIndex(){
    //   return this.mediaIndex;
    // }
  // }

  public ProductVariationModel(CreateVariationByParentId body, String category){
    this.parentId = body.parentId();
    this.stockInfo = body.stockInfo() != null ? body.stockInfo() : new StockInfo();
    // this.variationOptions = new ArrayList<VariationOptions>();
    this.variationOptions = new VariationOptions();
    this.variationName = body.variationName();
    this.image = new ArrayList<Image>();
    this.category = category;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  public ProductVariationModel(ProductVariationModel body, String category){
    this.parentId = body.getParentId();
    this.stockInfo = body.getStockInfo() != null ? body.getStockInfo() : new StockInfo();
    // this.variationOptions = body.getVariationOptions().isEmpty() ? new ArrayList<VariationOptions>() : body.getVariationOptions();
    this.variationOptions = body.getVariationOptions();
    this.variationName = body.getVariationName();
    this.image = body.getImage().isEmpty() ? new ArrayList<Image>() : body.getImage();
    this.category = category;
    this.createdAt = body.getCreatedAt();
    this.updatedAt = body.getUpdatedAt();
  }

}
