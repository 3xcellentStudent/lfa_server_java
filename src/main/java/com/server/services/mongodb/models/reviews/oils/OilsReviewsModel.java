package com.server.services.mongodb.models.reviews.oils;

import com.server.services.mongodb.models.reviews.ReviewsModel.Reviews;

public class OilsReviewsModel {
  public Reviews reviews;

  public OilsReviewsModel(){}

  public OilsReviewsModel(OilsReviewsModel reviewsModel){
    this.reviews = reviewsModel.reviews;
  }
}
