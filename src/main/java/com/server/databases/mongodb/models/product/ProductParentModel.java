package com.server.databases.mongodb.models.product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.databases.mongodb.dto.product.CreateNewProductDto;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.reviews.components.ReviewsSnapshot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@CompoundIndexes({
  @CompoundIndex(name = "category_rating_idx", def = "{'category': 1, 'rating': -1}"),
  @CompoundIndex(name = "category_created_idx", def = "{'category': 1, 'createdAt': -1}")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ProductParentModel {

  private @Id String id;
  private Integer rating;
  private @NotBlank @TextIndexed String productName;
  private @NotEmpty Descriptions descriptions;
  private @Builder.Default List<ProductVariationModel> variations = List.of();
  private @NotEmpty Specifications specifications;
  private List<MediaContent> mediaContent;
  private ReviewsSnapshot reviewsSnapshot;
  private @Indexed @NotBlank String category;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Descriptions {
    public @NotBlank String summary;
    public @NotEmpty List<String> presentable;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MediaContent {
    public boolean isImage;
    public ArrayList<MediaObject> mediaArray;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MediaObject {
      public String media;
      public String src;
    }
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Specifications {
    public ArrayList<String> titles;
    public ArrayList<Properties> properties;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
      public String name;
      public ArrayList<PropertiesArrayObject> array;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertiesArrayObject {
      public String name;
      public String value;
    }
  }

  // public void setId(String id){
  //   this.id = id;
  // }

  // public String getId(){
  //   return this.id;
  // }

  // public void setReviewsId(ArrayList<String> reviewsId){
  //   this.reviewsIds = reviewsId;
  // }

  // public ArrayList<String> getReviewsId(){
  //   return this.reviewsIds;
  // }

  // public Long getCreatedAt(){
  //   return this.createdAt;
  // }

  // public void setCreatedAt(Long newTime){
  // public void setCreatedAt(Long newTime){
  //   this.createdAt = newTime;
  // }

  // public Long getUpdatedAt(){
  //   return this.updatedAt;
  // }

  // public void setUpdatedAt(Long newTime){
  //   this.updatedAt = newTime;
  // }

  // public void setReviewsSnapshot(ReviewsSnapshot reviewsSnapshot){
  //   this.reviewsSnapshot = reviewsSnapshot;
  // }

  // public void setReviewsSnapshot(){
  //   this.reviewsSnapshot = new ReviewsSnapshot();
  // }

  // public ArrayList<MediaContent> getMediaContent(){
  //   return this.mediaContent;
  // }

  // public void setMediaContent(ArrayList<MediaContent> mediaContent){
  //   this.mediaContent = mediaContent;
  // }

  // public String getProductName(){
  //   return this.productName;
  // }

  // public void setVariationEntitiesId(ArrayList<String> variationEntitiesId){
  //   this.variationEntitiesId = variationEntitiesId;
  // }

  // public ArrayList<String> getVariationEntitiesId(){
  //   return this.variationEntitiesId;
  // }

  // public List<ProductVariationModel> getVariationEntities(){
  //   return this.variationEntities;
  // }

  // public void setVariationEntities(List<ProductVariationModel> variationEntities){
  //   this.variationEntities = variationEntities;
  // }

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

  // public ReviewsSnapshot getReviewsSnapshot(){
  //   return this.reviewsSnapshot;
  // }

  // public Integer getRating(){
  //   return this.rating;
  // }

  // public Descriptions getDescriptions(){
  //   return this.descriptions;
  // }

  // public Specifications getSpecifications(){
  //   return this.specifications;
  // }

  // public String getCategory(){
  //   return this.category;
  // }

  // public ProductParentModel(){}

  public ProductParentModel(CreateNewProductDto data){
    // this.reviewsIds = new ArrayList<>();
    this.rating = 0;
    this.productName = data.productName();
    this.descriptions = data.descriptions();
    // this.variationEntitiesId = new ArrayList<>();
    this.variations =  List.of();
    this.specifications = data.specifications();
    this.mediaContent = data.mediaContent();
    this.category = data.category();
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