package com.server.databases.mongodb.services.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.reviews.ReviewsModel;

@Service
public class ReviewsService {

  @Value("${databases.mongodb.collections.reviews}")
  private String collection;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> createOne(ReviewsModel body){
    ReviewsModel savedReview = mongoTemplate.insert(body, collection);

    return ResponseEntity.ok(savedReview);
  }

}
