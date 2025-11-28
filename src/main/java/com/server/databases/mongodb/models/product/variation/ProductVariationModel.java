package com.server.databases.mongodb.models.product.variation;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Component
public class ProductVariationModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty @NotBlank @NonNull private String parentId;
  @JsonProperty @NotBlank @NonNull private StockInfo stockInfo;
  @JsonProperty @NotBlank @NonNull private String variationName;
  @JsonProperty private List<ProductOption> productOptions;
  @JsonProperty private List<Image> image;
  @JsonProperty @NotBlank private String collectionName;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Image {
    public String media;
    public String src;
  }

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

  public void setParentId(String parentId){
    this.parentId = parentId;
  }

  public List<Image> getImage(){
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

  public void setPrice(String price){
    this.getStockInfo().price = price;
  }

  public ProductVariationModel(){}

  public ProductVariationModel(ProductVariationModel body){
    this.id = null;
    this.parentId = body.getParentId();
    this.stockInfo = body.getStockInfo() != null ? body.getStockInfo() : new StockInfo();
    this.productOptions = body.getProductOptions().isEmpty() ? new ArrayList<ProductOption>() : body.getProductOptions();
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
  //   this.productOptions = dataModel.getProductOptions();
  //   this.variationName = dataModel.getVariationName();
  //   this.image = new ArrayList<Image>();
  //   this.collectionName = dataModel.getCollectionName();
  //   this.createdAt = dataModel.getCreatedAt();
  //   this.updatedAt = dataModel.getUpdatedAt();
  // }
}
