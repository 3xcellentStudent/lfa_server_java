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
    private String media;
    private String src;
    private String srcset;

    public String getMedia(){
      return this.media;
    }

    public String getSrc(){
      return this.src;
    }

    public String getSrcset(){
      return this.srcset;
    }
  }

  public static class Descriptions {
    private String summary;
    private String[] presentable;

    public String getSummary(){
      return this.summary;
    }

    public String[] getPresentable(){
      return this.presentable;
    }
  }

  public static class StockInfo {
    @Min(0) private int stockAmountMax;
    @Min(0) private int stockAmountAvailable;
    @Min(0) private int stockAmountReserved;
    @Min(0) @NotNull private Long priceInCents;
    @NotBlank(message = "Cannot be blank") private String currency;

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
    private String name = "";
    private String type = "";
    private String value = "";

    public String getName(){
      return this.name;
    }

    public String getType(){
      return this.type;
    }

    public String getValue(){
      return this.value;
    }

    public VariationOptions(){}
  }

  public static class Item {
    private String value;
    private String fill;
    private String stroke;
    private Boolean stockStatus;
    private Integer mediaIndex;

    public String getValue(){
      return this.value;
    }

    public String getFill(){
      return this.fill;
    }

    public String getStroke(){
      return this.stroke;
    }

    public Boolean getStockStatus(){
      return this.stockStatus;
    }

    public Integer getMediaIndex(){
      return this.mediaIndex;
    }
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

}
