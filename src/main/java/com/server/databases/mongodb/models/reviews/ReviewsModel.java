package com.server.databases.mongodb.models.reviews;

import java.util.List;

public class ReviewsModel {

  public static class ReviewsSnapshot {
    public int five;
    public int four;
    public int three;
    public int two;
    public int one;
  }

  public static class Review {
    public int index;
    public String name;
    public String text;
    public String title;
    public String rating;
    public List<String> attachments;
  }
  
}
