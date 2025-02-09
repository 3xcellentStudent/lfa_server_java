package com.server.services.mongodb.services.media.diffusers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;

@Service
public class DiffusersMediaService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createOne(String id, String parent_id, String reviews_id, long timestamp){
    try {
      DiffusersMediaModel newObject = new DiffusersMediaModel();

      newObject.setId(id);
      newObject.setParentId(parent_id);
      newObject.setReviewsId(reviews_id);
      newObject.setCreateAt(timestamp);
      newObject.setUpdateAt(timestamp);

      DiffusersMediaModel response = mongoTemplate.save(newObject);

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
      DiffusersMediaModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersMediaModel.class);

      requestBodyObject.setId(id);
      requestBodyObject.setCreateAt(timestamp);
      requestBodyObject.setUpdateAt(timestamp);

      DiffusersMediaModel savedReviews = mongoTemplate.save(requestBodyObject);

      String response = objectMapper.writeValueAsString(savedReviews);
      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

}
