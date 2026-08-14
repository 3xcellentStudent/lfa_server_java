package com.server.databases.mongodb.models.reviews;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ReviewsModel {

  private @Id String id;
  private @NotBlank String parentId;
  private @NotBlank String content;
  private @NotBlank String firstName;
  private @NotBlank String lastName;
  private @NotBlank String title;
  private @Min(0) @Default Float rating = 0f;
  private List<String> attachments;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;

  public ReviewsModel(ReviewsModel dataModel){
    this.content = dataModel.content;
    this.firstName = dataModel.firstName;
    this.lastName = dataModel.lastName;
    this.title = dataModel.title;
    this.rating = dataModel.rating;
    this.attachments = dataModel.attachments;
    this.createdAt = dataModel.createdAt;
    this.updatedAt = dataModel.updatedAt;
    this.parentId = dataModel.parentId;
  }
}
