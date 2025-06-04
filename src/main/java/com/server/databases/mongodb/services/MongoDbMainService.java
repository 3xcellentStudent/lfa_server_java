package com.server.databases.mongodb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.result.UpdateResult;
import com.server.databases.mongodb.dto.DeleteManyFromArray;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;

@Service
public class MongoDbMainService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;

  private Logger logger = LoggerFactory.getLogger(MongoDbMainService.class);

  public <T> ResponseEntity<Object> updateNewOneById(String requestBodyString, Class<T> someClass, String collectionName){
    try {
      UpdateOneByIdDto requestBodyObject = objectMapper.readValue(requestBodyString, UpdateOneByIdDto.class);

      long timestamp = System.currentTimeMillis();

      Query query = Query.query(Criteria.where("id").is(requestBodyObject.getId()));
      Update update = QueriesHelper.getUpdateForNonArray(requestBodyObject.getField(), requestBodyObject.getNewData());
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      // T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass);
      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> updateNewOneById(UpdateOneByIdDto requestBodyObject, Class<T> someClass, String collectionName){
    try {
      long timestamp = System.currentTimeMillis();

      Query query = Query.query(Criteria.where("id").is(requestBodyObject.getId()));
      Update update = QueriesHelper.getUpdateForNonArray(requestBodyObject.getField(), requestBodyObject.getNewData());
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> pushNewOneToArrayById(String requestBodyString, Class<T> someClass, String collectionName){
    try {
      UpdateOneByIdDto requestBodyObject = objectMapper.readValue(requestBodyString, UpdateOneByIdDto.class);

      long timestamp = System.currentTimeMillis();

      Query query = Query.query(Criteria.where("id").is(requestBodyObject.getId()));
      Update update = QueriesHelper.getUpdateForArray(requestBodyObject.getField(), requestBodyObject.getNewData());
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> pushNewOneToArrayById(UpdateOneByIdDto requestBodyObject, Class<T> someClass, String collectionName){
    try {
      long timestamp = System.currentTimeMillis();

      Query query = Query.query(Criteria.where("id").is(requestBodyObject.getId()));
      Update update = QueriesHelper.getUpdateForArray(requestBodyObject.getField(), requestBodyObject.getNewData());
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

      String response = objectMapper.writeValueAsString(modifiedProduct);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
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
  //   } catch(JsonProcessingException error){
  //     error.printStackTrace();
  //     System.err.println(error.getMessage());
  //     return ResponseEntity.internalServerError().body("Internal server error in class " + this.getClass().getName());
  //   }
  // }

  public <T> ResponseEntity<Object> deleteManyFromArrayById(String requestBodyString, Class<T> someClass, String collectionName) {
    try {
      DeleteManyFromArray requestBodyObject = objectMapper.readValue(requestBodyString, DeleteManyFromArray.class);
      List<Integer> indexes = requestBodyObject.getIndexes();
      String selector = requestBodyObject.getSelector();
      String id = requestBodyObject.getId();

      // 1. Обнуляем указанные индексы (заменяем на null)
      Update update = QueriesHelper.doUnsetForArray(indexes, selector);
      Query query = Query.query(Criteria.where("id").is(id));
      mongoTemplate.updateMulti(query, update, someClass, collectionName);

      // 2. Удаляем все null из массива
      update = new Update().pull(selector, null);
      UpdateResult updatedObject = mongoTemplate.updateMulti(query, update, someClass);

      String response = objectMapper.writeValueAsString(updatedObject);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> deleteAllById(List<String> id, Class<T> someClass, String collectionName){
    try {
      Query query = Query.query(Criteria.where("id").in(id));

      List<T> removedObjects = mongoTemplate
      .findAllAndRemove(query, someClass, collectionName);

      String response = objectMapper.writeValueAsString(removedObjects);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> findAll(Class<T> someClass, String collectionName){
    try {
      List<T> foundObjects = mongoTemplate.findAll(someClass, collectionName).stream()
      .filter(Objects::nonNull).toList();

      if(foundObjects.isEmpty()){
        return ResponseEntity.status(404).body(new ArrayList<>());
      } else {
        String response = objectMapper.writeValueAsString(foundObjects);

        return ResponseEntity.ok(response);
      }
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> List<T> findAllById(String selector, List<String> id, Class<T> someClass, String collectionName){
    // try {
      Query query = Query.query(Criteria.where(selector).in(id));
      List<T> foundObjectsList = mongoTemplate.find(query, someClass, collectionName)
      .stream().filter(Objects::nonNull).toList();

      return foundObjectsList;

      // String response = objectMapper.writeValueAsString(foundObjectsList);

      // return ResponseEntity.ok(response); 
    // } catch(JsonProcessingException error){
    //   System.err.println(error.getMessage());
    //   error.printStackTrace();
    //   return null;
    //   // return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    // }
  }

  public <T> List<T> findAllById(String selector, String id, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where(selector).is(id));
    List<T> foundObject = mongoTemplate.find(query, someClass, collectionName);

    return foundObject;
  }

  public <T> T findById(String id, Class<T> someClass, String collectionName){
    T foundObject = mongoTemplate.findById(id, someClass, collectionName);

    return foundObject;
  }

  public <T> ResponseEntity<Object> getAsResponseEntity(T someClass){
    try {
      String response = objectMapper.writeValueAsString(someClass);

      return ResponseEntity.ok(response);
    } catch(JsonProcessingException error){
      String message = "Error occured while processing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

  public <T> ResponseEntity<Object> clearCollection(Class<T> someClass, String collectionName){
    mongoTemplate.remove(new Query(), someClass, collectionName);

    return ResponseEntity.ok("All products have been removed from database !");
  }

}
