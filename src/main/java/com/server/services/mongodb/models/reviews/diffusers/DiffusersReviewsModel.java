package com.server.services.mongodb.models.reviews.diffusers;

import org.springframework.data.annotation.Id;

import com.server.services.mongodb.models.reviews.ReviewsModel.Reviews;

public class DiffusersReviewsModel {

  @Id
  private String _id;
  private String parent_id;
  public Reviews reviews;

  public DiffusersReviewsModel(){}
  
  public DiffusersReviewsModel(DiffusersReviewsModel reviewsModel){
    this.reviews= reviewsModel.reviews;
  }

  public void setId(String _id){
    this._id = _id;
  }

  public String getId(){
    return this._id;
  }

  public void setParentId(String parent_id){
    this.parent_id = parent_id;
  }

  public String getParentId(){
    return this.parent_id;
  }
}
