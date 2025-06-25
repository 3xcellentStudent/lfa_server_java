package com.server.databases.mongodb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.DeleteManyFromArray;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;

@Service
public class MongoDbMainService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public <T> ResponseEntity<Object> updateNewOneById(UpdateOneByIdDto body, Class<T> someClass, String collectionName){
    long timestamp = System.currentTimeMillis();

    Query query = Query.query(Criteria.where("id").is(body.getId()));
    Update update = QueriesHelper.getUpdateForNonArray(body.getField(), body.getNewData());
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    update.set("updatedAt", timestamp);

    T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

    return ResponseEntity.ok(modifiedProduct);
  }

  public <T> ResponseEntity<Object> pushNewOneToArrayById(UpdateOneByIdDto body, Class<T> someClass, String collectionName){
    long timestamp = System.currentTimeMillis();

    Query query = Query.query(Criteria.where("id").is(body.getId()));
    Update update = QueriesHelper.getUpdateForArray(body.getField(), body.getNewData());
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    update.set("updatedAt", timestamp);

    T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

    return ResponseEntity.ok(modifiedProduct);
  }

  public <T> ResponseEntity<Object> deleteManyFromArrayById(DeleteManyFromArray body, Class<T> someClass, String collectionName) {
    List<Integer> indexes = body.getIndexes();
    String selector = body.getSelector();
    String id = body.getId();

    // 1. Обнуляем указанные индексы (заменяем на null)
    Update update = QueriesHelper.doUnsetForArray(indexes, selector);
    Query query = Query.query(Criteria.where("id").is(id));
    mongoTemplate.updateMulti(query, update, someClass, collectionName);

    // 2. Удаляем все null из массива
    update = new Update().pull(selector, null);
    UpdateResult updatedObject = mongoTemplate.updateMulti(query, update, someClass, collectionName);

    return ResponseEntity.ok(updatedObject);
  }

  public <T> ResponseEntity<Object> deleteManyById(DeleteManyById body, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where("id").in(body.getId()));

    List<T> removedObjects = mongoTemplate.findAllAndRemove(query, someClass, collectionName);

    return ResponseEntity.ok(removedObjects);
  }

  public <T> ResponseEntity<Object> deleteManyById(List<String> id, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));

    List<T> removedObjects = mongoTemplate.findAllAndRemove(query, someClass, collectionName);

    return ResponseEntity.ok(removedObjects);
  }

  public <T> ResponseEntity<Object> findAll(Class<T> someClass, String collectionName){
    List<T> foundObjects = mongoTemplate.findAll(someClass, collectionName).stream()
    .filter(Objects::nonNull).toList();

    if(foundObjects.isEmpty()){
      return ResponseEntity.status(404).body(new ArrayList<>());
    } else {
      return ResponseEntity.ok(foundObjects);
    }
  }

  public <T> List<T> findAllById(String selector, List<String> id, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where(selector).in(id));
    List<T> foundObjectsList = mongoTemplate.find(query, someClass, collectionName)
    .stream().filter(Objects::nonNull).toList();

    return foundObjectsList;
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

  // public <T> ResponseEntity<Object> getAsResponseEntity(T someClass){
  //   try {
  //     String response = objectMapper.writeValueAsString(someClass);

  //     return ResponseEntity.ok(response);
  //   } catch(JsonProcessingException error){
  //     String message = "Error occured while processing JSON !";
  //     logger.error(message, error);
  //     return ResponseEntity.internalServerError().body(message);
  //   }
  // }

  public <T> ResponseEntity<Object> clearCollection(Class<T> someClass, String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), someClass, collectionName);

    return ResponseEntity.ok(result);
  }

}
