package com.server.databases.mongodb.models.reviews;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@Component
public class ReviewsModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty @NotBlank private String parentId;
  @JsonProperty @NotBlank private String content;
  @JsonProperty @NotBlank private String firstName;
  @JsonProperty @NotBlank private String lastName;
  @JsonProperty @NotBlank private String title;
  @JsonProperty private int rating;
  @JsonProperty private List<String> attachments;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;
  @JsonProperty private String collectionName;

  public ReviewsModel(){}
  
  public ReviewsModel(ReviewsModel dataModel){
    this.content = dataModel.content;
    this.firstName = dataModel.firstName;
    this.lastName = dataModel.lastName;
    this.title = dataModel.title;
    this.rating = dataModel.rating;
    this.attachments = dataModel.attachments;
    this.createdAt = dataModel.createdAt;
    this.updatedAt = dataModel.updatedAt;
    this.collectionName = dataModel.collectionName;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getContent() {
    return content;
  }

  // public void setContent(String content) {
  //   this.content = content;
  // }

  public String getFirstName() {
    return firstName;
  }

  // public void setFirstName(String firstName) {
  //   this.firstName = firstName;
  // }

  public String getLastName() {
    return lastName;
  }

  // public void setLastName(String lastName) {
  //   this.lastName = lastName;
  // }

  public String getTitle() {
    return title;
  }

  // public void setTitle(String title) {
  //   this.title = title;
  // }

  public int getRating() {
    return rating;
  }

  // public void setRating(int rating) {
  //   this.rating = rating;
  // }

  public List<String> getAttachments() {
    return attachments;
  }

  // public void setAttachments(List<String> attachments) {
  //   this.attachments = attachments;
  // }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt() {
    this.createdAt = System.currentTimeMillis();
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt() {
    this.updatedAt = System.currentTimeMillis();
  }

  // public void setReviewsSnapshot(ReviewsSnapshot reviewsSnapshot) {
  //   this.reviewsSnapshot = reviewsSnapshot;
  // }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

}
