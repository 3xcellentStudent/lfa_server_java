package com.server.databases.mongodb.services;

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
import com.server.databases.mongodb.helpers.bodies.UpdateOneById;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;

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

  public <T> ResponseEntity<Object> updateNewOneById(UpdateOneById requestBodyObject, Class<T> someClass){
    try {
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

  public <T> ResponseEntity<Object> pushNewOneToArrayById(UpdateOneById requestBodyObject, Class<T> someClass){
    try {
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

  public <T> ResponseEntity<Object> deleteManyFromArrayById(List<Integer> indexes, String selector, Class<T> someClass){
    try {
      Update update = QueriesHelper.doUnsetForArray(indexes, selector);

      mongoTemplate.updateMulti(QueriesHelper.getId("index", indexes), update, selector);

      update = new Update().pull(selector, null);

      mongoTemplate.updateMulti(QueriesHelper.getId("index", indexes), update, someClass);

      return ResponseEntity.ok().build();
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error in class " + this.getClass().getName());
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
      return ResponseEntity.internalServerError().body("Internal server error in class " + this.getClass().getName());
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

  public <T> List<T> findAllById(String selector, List<String> id, Class<T> someClass){
    // try {
      List<T> foundObjectsList = mongoTemplate.find(QueriesHelper.getId(selector, id), someClass)
      .stream().filter(Objects::nonNull).toList();

      return foundObjectsList;

      // String response = objectMapper.writeValueAsString(foundObjectsList);

      // return ResponseEntity.ok(response); 
    // } catch(Exception error){
    //   System.err.println(error.getMessage());
    //   error.printStackTrace();
    //   return null;
    //   // return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    // }
  }

  public <T> List<T> findAllById(String selector, String id, Class<T> someClass){
    // try {
      List<T> foundObject = mongoTemplate.find(QueriesHelper.getId(selector, id), someClass);

      return foundObject;

      // String response = objectMapper.writeValueAsString(foundObjectsList);

      // return ResponseEntity.ok(response); 
    // } catch(Exception error){
    //   System.err.println(error.getMessage());
    //   error.printStackTrace();
    //   return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    // }
  }

  // public <T> ResponseEntity<Object> findAllById(String id, Class<T> someClass){
  public <T> T findById(String id, Class<T> someClass){
    // try {
      T foundObject = mongoTemplate.findById(id, someClass);

      return foundObject;

      // String response = objectMapper.writeValueAsString(foundObjectsList);

      // return ResponseEntity.ok(response); 
    // } catch(Exception error){
    //   System.err.println(error.getMessage());
    //   error.printStackTrace();
    //   return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    // }
  }

  public <T> ResponseEntity<Object> getAsResponseEntity(T someObject){
    try {
      String response = objectMapper.writeValueAsString(someObject);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println("Internal server error: " + error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }
}
