package com.server.services.mongodb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.helpers.bodies.RequestBodies.UpdateOneById;
import com.server.services.mongodb.helpers.queries.QueriesHelper;

@Service
public class MainService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  
  public <T> ResponseEntity<Object> updateNewOneById(String requestBodyString, Class<T> someClass){
    try {
      UpdateOneById requestBodyObject = objectMapper.readValue(requestBodyString, UpdateOneById.class);

      long timestamp = System.currentTimeMillis();

      Query query = QueriesHelper.getId("id", requestBodyObject.id);
      Update update = QueriesHelper.getUpdateForNonArray(requestBodyObject.field, requestBodyObject.newData);
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public <T> ResponseEntity<Object> pushNewOneToArrayById(String requestBodyString, Class<T> someClass){
    try {
      UpdateOneById requestBodyObject = objectMapper.readValue(requestBodyString, UpdateOneById.class);

      System.out.println(requestBodyObject.field);
      System.out.println(requestBodyObject.newData);

      long timestamp = System.currentTimeMillis();

      Query query = QueriesHelper.getId("id", requestBodyObject.id);
      Update update = QueriesHelper.getUpdateForArray(requestBodyObject.field, requestBodyObject.newData);
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public <T> ResponseEntity<Object> deleteAllById(List<String> id, Class<T> someClass){
    try {
      List<T> removedObjects = mongoTemplate
      .findAllAndRemove(QueriesHelper.getId("id", id), someClass);

      String response = objectMapper.writeValueAsString(removedObjects);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public <T> ResponseEntity<Object> findAll(Class<T> someClass){
    try {
      List<T> foundObjects = mongoTemplate.findAll(someClass).stream()
      .filter(Objects::nonNull).toList();

      if(foundObjects.isEmpty()){
        return ResponseEntity.status(404).body(new ArrayList<>());
      } else {
        String response = objectMapper.writeValueAsString(foundObjects);

        return ResponseEntity.ok(response);
      }
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public <T> ResponseEntity<Object> findAllById(List<String> id, Class<T> someClass){
    try {
      List<T> foundObjectsList = mongoTemplate.find(QueriesHelper.getId("id", id), someClass)
      .stream().filter(Objects::nonNull).toList();

      String response = objectMapper.writeValueAsString(foundObjectsList);

      return ResponseEntity.ok(response); 
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

}
