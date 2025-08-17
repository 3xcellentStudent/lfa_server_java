package com.server.databases.mongodb.models.products;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.models.products.variations.ProductVariations;
import com.server.databases.mongodb.models.reviews.components.ReviewsSnapshot;

@Component
public class ProductsModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty private ArrayList<String> reviewsId = new ArrayList<>();
  @JsonProperty private String mediaId;
  @JsonProperty private List<String> productVariationsIds;
  @JsonProperty private String rating;
  @JsonProperty @NotBlank private String title;
  @JsonProperty private Descriptions descriptions;
  @JsonProperty private List<ProductVariations> productVariations;
  @JsonProperty private Specifications specifications;
  @JsonProperty private MediaModel mediaContent;
  @JsonProperty private ReviewsSnapshot reviewsSnapshot;
  @JsonProperty @NotBlank private String collectionName;
  @JsonProperty @NotBlank private String category;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public static class Descriptions {
    public String summary;
    public String[] presentable;
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

  public void setProductVariationsIds(List<String> productVariationsIds){
    this.productVariationsIds = productVariationsIds;
  }

  public List<String> getProductVariationsIds(){
    return this.productVariationsIds;
  }

  public List<ProductVariations> getProductVariations(){
    return this.productVariations;
  }

  public void setProductVariations(List<ProductVariations> productVariations){
    this.productVariations = productVariations;
  }

  public void pushProductVariation(ProductVariations productVariation){
    this.productVariations.add(productVariation);
  }

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

  public int getReviewsSnapshotSum(){
    return this.reviewsSnapshot.getTotal();
  }

  public ReviewsSnapshot getReviewsSnapshot(){
    return this.reviewsSnapshot;
  }

  public String getRating(){
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

  public ProductsModel(){}

  public ProductsModel(ProductsModel dataModel){
    this.id = dataModel.getId();
    this.reviewsId = dataModel.getReviewsId();
    this.mediaId = dataModel.getMediaId();
    this.rating = dataModel.getRating();
    this.title = dataModel.getTitle();
    this.descriptions = dataModel.getDescriptions();
    this.productVariationsIds = dataModel.getProductVariationsIds();
    this.productVariations = dataModel.getProductVariations();
    this.specifications = dataModel.getSpecifications();
    this.mediaContent = dataModel.getMediaContent();
    this.collectionName = dataModel.getCollectionName();
    this.reviewsSnapshot = dataModel.getReviewsSnapshot();
    this.createdAt = dataModel.getCreatedAt();
    this.updatedAt = dataModel.getUpdatedAt();
  }

}