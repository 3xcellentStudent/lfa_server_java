package com.server.databases.mongodb.models.products.variations;

import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class ProductVariations {

  @Id
  @JsonProperty private String id;
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty private StockInfo stockInfo;
  @JsonProperty @NotBlank private String variationName;
  @JsonProperty private List<ProductOption> productOptions;
  @JsonProperty private List<String> images;
  @JsonProperty @NotBlank private String collectionName;
  @JsonProperty @NotBlank private String category;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    @Min(0) public int quantityMax;
    @NotBlank(message = "\"price\" cannot be blank !")
    @Pattern(regexp = "^[0-9]+$", message = "\"id\" must contain only digits")
    public String price;
    @Min(0) public int quantityAvailable;
  }

  public static class ProductOption {
    public String name;
    public String type;
    public List<Item> items;
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

  public List<ProductOption> getProductOptions(){
    return this.productOptions;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

  public String getParentId(){
    return this.parentId;
  }

  public List<String> getImages(){
    return this.images;
  }

  public String getVariationName(){
    return this.variationName;
  }

  public void setVariationName(String variationName){
    this.variationName = variationName;
  }

  public ProductVariations(){}

  public ProductVariations(ProductVariations dataModel){
    this.id = dataModel.getId();
    this.parentId = dataModel.getParentId();
    this.stockInfo = dataModel.getStockInfo();
    this.productOptions = dataModel.getProductOptions();
    this.variationName = dataModel.getVariationName();
    this.images = dataModel.getImages();
    this.collectionName = dataModel.getCollectionName();
    this.createdAt = dataModel.getCreatedAt();
    this.updatedAt = dataModel.getUpdatedAt();
  }
}
