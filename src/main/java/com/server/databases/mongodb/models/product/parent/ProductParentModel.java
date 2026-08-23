package com.server.databases.mongodb.models.product.parent;

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

  public ProductParentModel(CreateNewProductDto data){
    this.rating = 0;
    this.productName = data.productName();
    this.descriptions = data.descriptions();
    this.variations =  List.of();
    this.specifications = data.specifications();
    this.mediaContent = data.mediaContent();
    this.category = data.category();
    this.reviewsSnapshot = new ReviewsSnapshot();
  }

}