package com.server.services.mongodb.models.reviews.diffusers;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.server.services.mongodb.models.reviews.ReviewsModel.Review;
import com.server.services.mongodb.models.reviews.ReviewsModel.ReviewsSnapshot;

@Component
@Document(collection = "reviews_diffusers")
public class DiffusersReviewsModel {

  @Id
  private String id;
  private String parentId;
  private String mediaId;
  public int countOfReviews;
  public ReviewsSnapshot reviewsSnapshot;
  public List<Review> reviewsList;
  private long createdAt;
  private long updatedAt;

  public DiffusersReviewsModel(){}
  
  public DiffusersReviewsModel(DiffusersReviewsModel dataModel){
    this.countOfReviews = dataModel.countOfReviews;
    this.reviewsList = dataModel.reviewsList;
    this.reviewsSnapshot = dataModel.reviewsSnapshot;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public void setParentId(String parentId){
    this.parentId = parentId;
  }

  public String getParentId(){
    return this.parentId;
  }

  public void setMediaId(String mediaId){
    this.mediaId = mediaId;
  }

  public String getMediaId(){
    return this.mediaId;
  }

  public long getCreateAt(){
    return this.createdAt;
  }

  public void setCreateAt(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdateAt(){
    return this.updatedAt;
  }

  public void setUpdateAt(long newTime){
    this.updatedAt = newTime;
  }

  public String getCollectionName(){
    return "reviews_diffusers";
  }

}
