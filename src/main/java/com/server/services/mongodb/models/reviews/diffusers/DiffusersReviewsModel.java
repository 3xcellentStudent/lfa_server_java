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
  private String parent_id;
  private String media_id;
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

  public void setId(String _id){
    this.id = _id;
  }

  public String getId(){
    return this.id;
  }

  public void setParentId(String parent_id){
    this.parent_id = parent_id;
  }

  public String getParentId(){
    return this.parent_id;
  }

  public void setMediaId(String media_id){
    this.media_id = media_id;
  }

  public String getMediaId(){
    return this.media_id;
  }

  public long getCreateTime(){
    return this.createdAt;
  }

  public void setCreateTime(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdateTime(){
    return this.updatedAt;
  }

  public void setUpdateTime(long newTime){
    this.updatedAt = newTime;
  }

  public String getCollectionName(){
    return "reviews_diffusers";
  }

}
