package com.server.databases.mongodb.models.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.models.reviews.components.ReviewsSnapshot;

@Component
public class ProductsModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty private ArrayList<String> reviewsId = new ArrayList<>();
  @JsonProperty private String mediaId;
  @JsonProperty private String rating;
  @JsonProperty private String title;
  @JsonProperty private Descriptions descriptions;
  @JsonProperty private StockInfo stockInfo;
  @JsonProperty private List<ProductOption> productOptions;
  @JsonProperty private Specifications specifications;
  @JsonProperty private MediaModel mediaContent;
  @Autowired
  @JsonProperty private ReviewsSnapshot reviewsSnapshot;
  @JsonProperty private String collectionName;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    public String category;
    public int quantityMax;
    public String price;
    @Autowired
    public ReviewsSnapshot reviewsSnapshot;
    public int countOfReviews;
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

  public static class MediaContent {
    public TitleContent titleContent;
    public List<List<Image>> images;

    public static class TitleContent {
      public String productLogo;
      public String descriptionVideo;
    }

    public static class Image {
      public String media;
      public String src;
    }
  }

  public static class Specifications {
      public List<String> titles;
      public List<Properties> properties;

    public static class Properties {
      public String name;
      public List<PropertiesArrayObject> array;
    }

    public static class PropertiesArrayObject {
      public String name;
      public String value;
    }
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public void setReviewsId(ArrayList<String> reviewsId){
    this.reviewsId = reviewsId;
  }

  public ArrayList<String> getReviewsId(){
    return this.reviewsId;
  }

  public void setMediaId(String mediaId){
    this.mediaId = mediaId;
  }

  public String getMediaId(){
    return this.mediaId;
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

  public MediaModel getMediaContent(){
    return this.mediaContent;
  }

  public void setMediaContent(MediaModel mediaContent){
    this.mediaContent = mediaContent;
  }

  public String getTitle(){
    return this.title;
  }

  public StockInfo getStockInfo(){
    return this.stockInfo;
  }

  public int getReviewsSnapshotByFieldName(String field){
    switch(field){
      case "five": return stockInfo.reviewsSnapshot.five;
      case "four": return stockInfo.reviewsSnapshot.four;
      case "three": return stockInfo.reviewsSnapshot.three;
      case "two": return stockInfo.reviewsSnapshot.two;
      case "one": return stockInfo.reviewsSnapshot.one;
      default: return stockInfo.reviewsSnapshot.five;
    }
  }

  public String getRating(){
    return this.rating;
  }

  public Descriptions getDescriptions(){
    return this.descriptions;
  }

  public List<ProductOption> getProductOptions(){
    return this.productOptions;
  }

  public Specifications getSpecifications(){
    return this.specifications;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

  public ReviewsSnapshot getReviewsSnapshot(){
    return this.stockInfo.reviewsSnapshot;
  }

  public ProductsModel(){}

  public ProductsModel(ProductsModel dataModel){
    this.id = dataModel.getId();
    this.reviewsId = dataModel.getReviewsId();
    this.mediaId = dataModel.getMediaId();
    this.rating = dataModel.getRating();
    this.title = dataModel.getTitle();
    this.descriptions = dataModel.getDescriptions();
    this.stockInfo = dataModel.getStockInfo();
    this.productOptions = dataModel.getProductOptions();
    this.specifications = dataModel.getSpecifications();
    this.mediaContent = dataModel.getMediaContent();
    this.collectionName = dataModel.getCollectionName();
    this.reviewsSnapshot = dataModel.getReviewsSnapshot();
    this.createdAt = dataModel.getCreatedAt();
    this.updatedAt = dataModel.getUpdatedAt();
  }

}