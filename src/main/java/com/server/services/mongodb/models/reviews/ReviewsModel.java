package com.server.services.mongodb.models.reviews;

import java.util.List;

public class ReviewsModel {
  public static class Reviews {
    public int countOfReviews;
    public ReviewsSnapshot reviewsSnapshot;
    public List<Review> reviewsList;
  }

  public static class ReviewsSnapshot {
    public int five;
    public int four;
    public int three;
    public int two;
    public int one;
  }

  public static class Review {
    public String name;
    public String text;
    public String title;
    public String rating;
    public List<String> attachments;
  }
  
}
