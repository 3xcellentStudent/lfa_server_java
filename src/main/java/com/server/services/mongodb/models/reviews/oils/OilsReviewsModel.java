package com.server.services.mongodb.models.reviews.oils;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviewsOils")
public class OilsReviewsModel {

  public OilsReviewsModel(){}

  public OilsReviewsModel(OilsReviewsModel reviewsModel){
    // this.reviews = reviewsModel.reviews;
  }
}
