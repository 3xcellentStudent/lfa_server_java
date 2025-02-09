package com.server.services.mongodb.services.reviews.diffusers;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.helpers.queries.QueriesHelper;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;

@Service
public class DiffusersReviewsService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createOne(String id, String parent_id, String media_id, long timestamp){
    try {
      DiffusersReviewsModel newObject = new DiffusersReviewsModel();

      newObject.setId(id);
      newObject.setParentId(parent_id);
      newObject.setMediaId(media_id);
      newObject.setCreateAt(timestamp);
      newObject.setUpdateAt(timestamp);
      newObject.reviewsList = new ArrayList<>();

      DiffusersReviewsModel response = mongoTemplate.save(newObject);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> createOne(String requestBodyString){
    String id = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    try {
      DiffusersReviewsModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersReviewsModel.class);

      requestBodyObject.setId(id);
      requestBodyObject.setCreateAt(timestamp);
      requestBodyObject.setUpdateAt(timestamp);

      DiffusersReviewsModel savedObject = mongoTemplate.save(requestBodyObject);

      String response = objectMapper.writeValueAsString(savedObject);
      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public DiffusersReviewsModel findOneAndSliceReviewsList(String id, int fromIndex, int toIndex){
    try {
      DiffusersReviewsModel slicedReview = mongoTemplate
      .findOne(QueriesHelper.getSliceOfReviewsList("id", id, fromIndex, toIndex), DiffusersReviewsModel.class);

      // String response = objectMapper.writeValueAsString(slicedReviews);

      // System.out.println("findOneAndSliceReviewsList method in class DiffusersReviewsService: " + response);

      return slicedReview;
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

}
