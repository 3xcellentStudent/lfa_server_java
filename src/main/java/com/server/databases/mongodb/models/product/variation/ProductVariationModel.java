package com.server.databases.mongodb.models.product.variation;


import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Component
public class ProductVariationModel {

  @Id @JsonProperty private String id;
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty @Valid private StockInfo stockInfo;
  @JsonProperty @NotBlank private String variationName;
  // @JsonProperty private ArrayList<VariationOptions> variationOptions;
  @JsonProperty private VariationOptions variationOptions;
  @JsonProperty private ArrayList<Image> image;
  @JsonProperty @Pattern(regexp = "^variation-.*$", message = "\"collectionName\" must contain \"variation\" and \"-\" !") private String collectionName;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Image {
    public String media;
    public String src;
    public String srcset;
  }

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    @Min(0) public int stockAmountMax;
    @Min(0) public int stockAmountAvailable;
    @Min(0) public int stockAmountReserved;
    @Min(0) @NotNull public Long priceInCents;
    @NotBlank(message = "Cannot be blank") public String currency;

    public int stockAmountMax(){
      return this.stockAmountMax;
    }

    public int getStockAmountAvailable(){
      return this.stockAmountAvailable;
    }

    public int getStockAmountReserved(){
      return this.stockAmountReserved;
    }

    public Long getPriceInCents(){
      return this.priceInCents;
    }

    public String getCurrency(){
      return this.currency;
    }

    public void setStockAmountMax(int newStockAmountMax){
      this.stockAmountMax = newStockAmountMax;
    }

    public void setStockAmountAvailable(int newStockAmountAvailable){
      this.stockAmountAvailable = newStockAmountAvailable;
    }

    public void setStockAmountReserved(int newStockAmountReserved){
      this.stockAmountReserved = newStockAmountReserved;
    }

    public void setPriceInCents(Long newPriceInCents){
      this.priceInCents = newPriceInCents;
    }

    public void setCurrency(String newCurrency){
      this.currency = newCurrency;
    }

    public StockInfo(){
      this.stockAmountMax = 0;
      this.stockAmountAvailable = 0;
      this.priceInCents = 0L;
      this.currency = "CAD";
    }
  }

  public static class VariationOptions {
    public String name;
    public String type;
    public String value;

    public VariationOptions(){}
  }

  public static class Item {
    public String value;
    public String fill;
    public String stroke;
    public boolean stockStatus;
    public int mediaIndex;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public long getCreatedAt(){
    return this.createdAt;
  }

  public void setCreatedAt(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdatedAt(){
    return this.updatedAt;
  }

  public void setUpdatedAt(long newTime){
    this.updatedAt = newTime;
  }

  public StockInfo getStockInfo(){
    return this.stockInfo;
  }

  // public ArrayList<VariationOptions> getVariationOptions(){
  public VariationOptions getVariationOptions(){
    return this.variationOptions;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

  public String getParentId(){
    return this.parentId;
  }

  public void setParentId(String parentId){
    this.parentId = parentId;
  }

  public ArrayList<Image> getImage(){
    return this.image;
  }

  public String getVariationName(){
    return this.variationName;
  }

  public void setVariationName(String variationName){
    this.variationName = variationName;
  }

  public void setStockInfo(){
    this.stockInfo = new StockInfo();
  }

  public void setPrice(Long priceInCents){
    this.getStockInfo().priceInCents = priceInCents;
  }

  public ProductVariationModel(){}

  public ProductVariationModel(CreateVariationByParentId body){
    this.parentId = body.getParentId();
    this.stockInfo = body.getStockInfo() != null ? body.getStockInfo() : new StockInfo();
    // this.variationOptions = new ArrayList<VariationOptions>();
    this.variationOptions = new VariationOptions();
    this.variationName = body.getVariationName();
    this.image = new ArrayList<Image>();
    this.collectionName = body.getCollectionName();
    this.createdAt = 0;
    this.updatedAt = 0;
  }

  public ProductVariationModel(ProductVariationModel body){
    this.parentId = body.getParentId();
    this.stockInfo = body.getStockInfo() != null ? body.getStockInfo() : new StockInfo();
    // this.variationOptions = body.getVariationOptions().isEmpty() ? new ArrayList<VariationOptions>() : body.getVariationOptions();
    this.variationOptions = body.getVariationOptions();
    this.variationName = body.getVariationName();
    this.image = body.getImage().isEmpty() ? new ArrayList<Image>() : body.getImage();
    this.collectionName = body.getCollectionName();
    this.createdAt = body.getCreatedAt();
    this.updatedAt = body.getUpdatedAt();
  }

  // public ProductVariationModel(ProductVariationModel dataModel){
  //   this.id = dataModel.getId();
  //   this.parentId = dataModel.getParentId();
  //   this.stockInfo = dataModel.getStockInfo();
  //   this.productOptions = dataModel.getVariationOptionss();
  //   this.variationName = dataModel.getVariationName();
  //   this.image = new ArrayArrayList<Image>();
  //   this.collectionName = dataModel.getCollectionName();
  //   this.createdAt = dataModel.getCreatedAt();
  //   this.updatedAt = dataModel.getUpdatedAt();
  // }
}
