package com.server.databases.mongodb.models.reviews;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Integer rating;
  private List<String> attachments;
  private @CreatedDate Instant createdAt;
  private @LastModifiedDate Instant updatedAt;
  private String category;

  public ReviewsModel(ReviewsModel dataModel){
    this.content = dataModel.content;
    this.firstName = dataModel.firstName;
    this.lastName = dataModel.lastName;
    this.title = dataModel.title;
    this.rating = dataModel.rating;
    this.attachments = dataModel.attachments;
    this.createdAt = dataModel.createdAt;
    this.updatedAt = dataModel.updatedAt;
    this.category = dataModel.category;
    this.parentId = dataModel.parentId;
    this.id = dataModel.id;
  }

  // public static class ReviewsSnapshot {
  //   public int five;
  //   public int four;
  //   public int three;
  //   public int two;
  //   public int one;
  // }

  // public static class Review {
  //   public int index;
  //   public String name;
  //   public String text;
  //   public String title;
  //   public String rating;
  //   public List<String> attachments;
  // }

  // public String getId() {
  //   return id;
  // }

  // public void setId(String id) {
  //   this.id = id;
  // }

  // public String getParentId() {
  //   return parentId;
  // }

  // public void setParentId(String parentId) {
  //   this.parentId = parentId;
  // }

  // public String getContent() {
  //   return content;
  // }

  // public String getFirstName() {
  //   return firstName;
  // }

  // public String getLastName() {
  //   return lastName;
  // }

  // public String getTitle() {
  //   return title;
  // }

  // public int getRating() {
  //   return rating;
  // }

  // public List<String> getAttachments() {
  //   return attachments;
  // }

  // public long getCreatedAt() {
  //   return createdAt;
  // }

  // public void setCreatedAt() {
  //   this.createdAt = System.currentTimeMillis();
  // }

  // public long getUpdatedAt() {
  //   return updatedAt;
  // }

  // public void setUpdatedAt() {
  //   this.updatedAt = System.currentTimeMillis();
  // }

  // public String getCollectionName() {
  //   return this.collection;
  // }

  // public void setCollectionName(String collection) {
  //   this.collection = collection;
  // }

}
