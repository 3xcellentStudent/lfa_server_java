package com.server.databases.mongodb.models.reviews.components;

import org.springframework.stereotype.Component;

@Component
public class ReviewsSnapshot {
  public int five;
  public int four;
  public int three;
  public int two;
  public int one;

  public int getTotal() {
    return five + four + three + two + one;
  }
}
