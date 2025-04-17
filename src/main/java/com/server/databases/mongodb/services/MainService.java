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
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.result.UpdateResult;
import com.server.databases.mongodb.helpers.bodies.DeleteManyFromArray;
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

  // public <T> ResponseEntity<Object> deleteManyFromArrayById(String requestBodyString, Class<T> someClass){
  //   try {
  //     DeleteManyFromArray requestBodyObject = objectMapper.readValue(requestBodyString, DeleteManyFromArray.class);
  //     List<Integer> indexes = requestBodyObject.getIndexes();
  //     String selector = requestBodyObject.getSelector();
  //     String id = requestBodyObject.getId();

  //     System.out.println(indexes.get(0));
  //     System.out.println(selector);
  //     System.out.println(id);

  //     Update update = QueriesHelper.doUnsetForArray(indexes, selector);

  //     System.out.println(update.toString());

  //     mongoTemplate.updateMulti(QueriesHelper.getId("id", id), update, selector);

  //     update = new Update().pull(selector, null);

  //     UpdateResult updatedObject = mongoTemplate.updateMulti(QueriesHelper.getId("id", id), update, someClass);

  //     String response = objectMapper.writeValueAsString(updatedObject);

  //     return ResponseEntity.ok(response);
  //   } catch(Exception error){
  //     error.printStackTrace();
  //     System.err.println(error.getMessage());
  //     return ResponseEntity.internalServerError().body("Internal server error in class " + this.getClass().getName());
  //   }
  // }

  public <T> ResponseEntity<Object> deleteManyFromArrayById(String requestBodyString, Class<T> someClass) {
    try {
        DeleteManyFromArray requestBodyObject = objectMapper.readValue(requestBodyString, DeleteManyFromArray.class);
        List<Integer> indexes = requestBodyObject.getIndexes();
        String selector = requestBodyObject.getSelector();
        String id = requestBodyObject.getId();

        // 1. Обнуляем указанные индексы (заменяем на null)
        Update update = QueriesHelper.doUnsetForArray(indexes, selector);
        mongoTemplate.updateMulti(QueriesHelper.getId("id", id), update, someClass);

        // 2. Удаляем все null из массива
        update = new Update().pull(selector, null);
        UpdateResult updatedObject = mongoTemplate.updateMulti(QueriesHelper.getId("id", id), update, someClass);

        String response = objectMapper.writeValueAsString(updatedObject);
        return ResponseEntity.ok(response);

    } catch (Exception error) {
        error.printStackTrace();
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

  public <T> ResponseEntity<Object> getAsResponseEntity(T someClass){
    try {
      String response = objectMapper.writeValueAsString(someClass);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println("Internal server error: " + error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  // public <T> ResponseEntity<Object> createOne(T someClass){
  //   try {
  //     T createdObject = mongoTemplate.save(someClass);

  //     String responseString = objectMapper.writeValueAsString(createdObject);

  //     return ResponseEntity.ok(responseString);
  //   } catch (MongoSocketException | MongoTimeoutException error) {
  //     return ResponseEntity.status(503).body("Database unavailable");
  //   } catch (IllegalArgumentException error) {
  //     return ResponseEntity.status(400).body("Invalid user data");
  //   } catch (Exception error) {
  //     return ResponseEntity.status(500).body("Unexpected error");
  //   }
  // }

}
