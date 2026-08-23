package com.server.databases.mongodb.services;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;

@Service
public class MongoDbMainService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public <T> T updateOneById(UpdateOneByIdDto body, Class<T> someClass, String collection){
    Query query = Query.query(Criteria.where("_id").is(body.id()));
    
    Update update = new Update();
    update.set(body.path(), body.data());
    update.set("updatedAt", Instant.now());
    
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    T modifiedDoc = mongoTemplate.findAndModify(query, update, options, someClass, collection);

    return modifiedDoc;
  }

  public <T> T updateOneById(String id, String collectionName, Update update, Class<T> someClass){
    Query query = Query.query(Criteria.where("_id").is(id));
    
    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    T modifiedDoc = mongoTemplate.findAndModify(query, update, options, someClass, collectionName);

    return modifiedDoc;
  }

  public <T> List<T> deleteManyById(List<String> ids, Class<T> someClass, String collection){
    Query query = Query.query(Criteria.where("_id").in(ids));

    List<T> removedObjects = mongoTemplate.findAllAndRemove(query, someClass, collection);

    return removedObjects;
  }

  public <T> Page<T> findByPage(int page, int validatedSize, String selector, Class<T> someClass, String collection){
    Pageable pageable = PageRequest.of(page, validatedSize);
    
    Query query = new Query().with(pageable).with(Sort.by(Sort.Direction.DESC, selector));

    List<T> list = mongoTemplate.find(query, someClass, collection);
    long total = mongoTemplate.count(new Query(), someClass, collection);

    return new PageImpl<>(list, pageable, total);
  }

  public <T> List<T> findManyById(String selector, List<String> id, Class<T> someClass, String collection){
    Query query = Query.query(Criteria.where(selector).in(id));
    
    List<T> documents = mongoTemplate.find(query, someClass, collection)
    .stream().filter(Objects::nonNull).toList();

    return documents;
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

  public <T> Long clearCollection(Class<T> someClass, String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), someClass, collectionName);

    return result.getDeletedCount();
  }

}
