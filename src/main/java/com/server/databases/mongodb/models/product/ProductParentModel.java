package com.server.databases.mongodb.models.product;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.dto.product.CreateNewProduct;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.reviews.components.ReviewsSnapshot;

import jakarta.validation.constraints.NotBlank;

@Component
public class ProductParentModel {

  @Id @JsonProperty private String id;
  // @JsonProperty private ArrayList<String> reviewsIds = new ArrayList<>();
  // @JsonProperty private ArrayList<String> variationEntitiesId;
  @JsonProperty private Integer rating;
  @JsonProperty @NotBlank private String productName;
  @JsonProperty @NotBlank private Descriptions descriptions;
  @JsonProperty private ArrayList<ProductVariationModel> variationEntities;
  @JsonProperty private Specifications specifications;
  @JsonProperty private ArrayList<MediaContent> mediaContent;
  @JsonProperty private ReviewsSnapshot reviewsSnapshot;
  @JsonProperty @NotBlank private String collectionName;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class MediaContent {
    public boolean isImage;
    public ArrayList<MediaObject> mediaArray;

    public static class MediaObject {
      public String media;
      public String src;
    }
  }

  public static class Specifications {
    public ArrayList<String> titles;
    public ArrayList<Properties> properties;

    public static class Properties {
      public String name;
      public ArrayList<PropertiesArrayObject> array;
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

  // public void setReviewsId(ArrayList<String> reviewsId){
  //   this.reviewsIds = reviewsId;
  // }

  // public ArrayList<String> getReviewsId(){
  //   return this.reviewsIds;
  // }

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

  public void setReviewsSnapshot(ReviewsSnapshot reviewsSnapshot){
    this.reviewsSnapshot = reviewsSnapshot;
  }

  public void setReviewsSnapshot(){
    this.reviewsSnapshot = new ReviewsSnapshot();
  }

  public ArrayList<MediaContent> getMediaContent(){
    return this.mediaContent;
  }

  public void setMediaContent(ArrayList<MediaContent> mediaContent){
    this.mediaContent = mediaContent;
  }

  public String getProductName(){
    return this.productName;
  }

  // public void setVariationEntitiesId(ArrayList<String> variationEntitiesId){
  //   this.variationEntitiesId = variationEntitiesId;
  // }

  // public ArrayList<String> getVariationEntitiesId(){
  //   return this.variationEntitiesId;
  // }

  public ArrayList<ProductVariationModel> getVariationEntities(){
    return this.variationEntities;
  }

  public void setVariationEntities(ArrayList<ProductVariationModel> variationEntities){
    this.variationEntities = variationEntities;
  }

  // public void pushProductVariation(ProductVariationModel productVariation){
  //   this.variationEntities.add(productVariation);
  // }

  public int getReviewsSnapshotByFieldName(String field){
    switch(field){
      case "five": return this.reviewsSnapshot.five;
      case "four": return this.reviewsSnapshot.four;
      case "three": return this.reviewsSnapshot.three;
      case "two": return this.reviewsSnapshot.two;
      case "one": return this.reviewsSnapshot.one;
      default: return this.reviewsSnapshot.five;
    }
  }

  public ReviewsSnapshot getReviewsSnapshot(){
    return this.reviewsSnapshot;
  }

  public Integer getRating(){
    return this.rating;
  }

  public Descriptions getDescriptions(){
    return this.descriptions;
  }

  public Specifications getSpecifications(){
    return this.specifications;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

  public ProductParentModel(){}

  public ProductParentModel(CreateNewProduct data){
    // this.reviewsIds = new ArrayList<>();
    this.rating = 0;
    this.productName = data.getProductName();
    this.descriptions = data.getDescriptions();
    // this.variationEntitiesId = new ArrayList<>();
    this.variationEntities =  new ArrayList<>();
    this.specifications = data.getSpecifications();
    this.mediaContent = data.getMediaContent();
    this.collectionName = data.getCollectionName();
    this.reviewsSnapshot = new ReviewsSnapshot();
  }

  // public ProductModel(ProductModel data){
    // this.id = data.getId();
    // this.reviewsId = data.getReviewsId();
    // this.rating = data.getRating();
    // this.mainName = data.getMainName();
    // this.descriptions = data.getDescriptions();
    // this.variationEntitiesId = data.getVariationEntitiesId();
    // this.variationEntities = data.getVariationEntities();
    // this.specifications = data.getSpecifications();
    // this.mediaContent = data.getMediaContent();
    // this.collectionName = data.getCollectionName();
    // this.reviewsSnapshot = data.getReviewsSnapshot();
    // this.createdAt = data.getCreatedAt();
    // this.updatedAt = data.getUpdatedAt();
  // }

}