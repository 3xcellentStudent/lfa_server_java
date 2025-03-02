package com.server.databases.mongodb.services.media.diffusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;
import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;

@Service
public class DiffusersMediaService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createOne(String id, String parentId, long timestamp){
    try {
      DiffusersMediaModel newObject = new DiffusersMediaModel();

      newObject.setId(id);
      newObject.setParentId(parentId);
      // newObject.setReviewsId(reviews_id);
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
    long timestamp = System.currentTimeMillis();

    try {
      DiffusersMediaModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersMediaModel.class);
      String id = requestBodyObject.getId();
      boolean isExists = mongoTemplate.exists(QueriesHelper.getId("id", id), DiffusersMediaModel.class);
      if(isExists){
        return ResponseEntity.status(303).body("Document already exists !");
      } else {
        requestBodyObject.setId(id);
        requestBodyObject.setCreateAt(timestamp);
        requestBodyObject.setUpdateAt(timestamp);
  
        DiffusersMediaModel savedReviews = mongoTemplate.save(requestBodyObject);
  
        String response = objectMapper.writeValueAsString(savedReviews);
        return ResponseEntity.ok(response);
      }
    } catch(Exception error){
      System.err.println("Internal server error: " + error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

}
