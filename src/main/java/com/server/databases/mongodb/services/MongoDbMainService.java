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
import org.springframework.http.HttpStatus;
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

  public <T> ResponseEntity<Object> updateNewOneById(UpdateOneByIdDto body, Class<T> someClass){
    long timestamp = System.currentTimeMillis();

    Query queryIsExist = Query.query(Criteria.where("id").is(body.getId()));

    boolean isExists = mongoTemplate.exists(queryIsExist, someClass, body.getCollectionName());

    if(isExists){
      Query query = Query.query(Criteria.where("id").is(body.getId()));
      Update update = QueriesHelper.getUpdateForNonArray(body.getField(), body.getNewData());
      FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

      update.set("updatedAt", timestamp);

      T modifiedProduct = mongoTemplate.findAndModify(query, update, options, someClass, body.getCollectionName());

      return ResponseEntity.ok(modifiedProduct);
    } else {
      String message = "Document with ID: \"" + body.getId() + "\" not found";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
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

    Update update = QueriesHelper.doUnsetForArray(indexes, selector);
    Query query = Query.query(Criteria.where("id").is(id));
    mongoTemplate.updateMulti(query, update, someClass, collectionName);

    update = new Update().pull(selector, null);
    UpdateResult updatedObject = mongoTemplate.updateMulti(query, update, someClass, collectionName);

    return ResponseEntity.ok(updatedObject);
  }

  public <T> ResponseEntity<Object> deleteManyById(DeleteManyById body, Class<T> someClass){
    Query query = Query.query(Criteria.where("id").in(body.getId()));

    List<T> removedObjects = mongoTemplate.findAllAndRemove(query, someClass, body.getCollectionName());

    return ResponseEntity.ok(removedObjects);
  }

  // public <T> ResponseEntity<Object> deleteManyById(List<String> id, Class<T> someClass, String collectionName){
  //   Query query = Query.query(Criteria.where("id").in(id));

  //   List<T> removedObjects = mongoTemplate.findAllAndRemove(query, someClass, collectionName);

  //   return ResponseEntity.ok(removedObjects);
  // }

  public <T> ResponseEntity<Object> findAll(Class<T> someClass, String collectionName){
    List<T> foundDocs = mongoTemplate.findAll(someClass, collectionName).stream()
    .filter(Objects::nonNull).toList();

    if(foundDocs.isEmpty()){
      return ResponseEntity.status(404).body(new ArrayList<>());
    } else {
      return ResponseEntity.ok(foundDocs);
    }
  }

  public <T> List<T> findManyById(String selector, List<String> id, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where(selector).in(id));
    List<T> foundDocsList = mongoTemplate.find(query, someClass, collectionName)
    .stream().filter(Objects::nonNull).toList();

    return foundDocsList;
  }

  public <T> List<T> findManyById(String selector, String id, Class<T> someClass, String collectionName){
    Query query = Query.query(Criteria.where(selector).is(id));
    List<T> foundObject = mongoTemplate.find(query, someClass, collectionName);

    return foundObject;
  }

  public <T> T findById(String id, Class<T> someClass, String collectionName){
    T foundObject = mongoTemplate.findById(id, someClass, collectionName);

    return foundObject;
  }

  public <T> ResponseEntity<Object> clearCollection(Class<T> someClass, String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), someClass, collectionName);

    return ResponseEntity.ok(result);
  }

  public boolean entityExistingInDatabase(String selector, Object entity, String collectionName){
    Query query = Query.query(Criteria.where(selector).is(entity));
    return mongoTemplate.exists(query, collectionName);
  }

}
