package com.server.databases.mongodb.services.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.databases.mongodb.dto.orders.request.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.models.orders.MainOrderModel;

@Service
@Transactional
public class OrdersService {

  @Value("${databases.mongodb.collections.orders}")
  private String collectionName;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> create(MainOrderModel body){
    MainOrderModel createdDocument = mongoTemplate.insert(body, collectionName);

    return ResponseEntity.ok(createdDocument);
  }

  @Transactional
  public ResponseEntity<Object> updateOneById(OrdersFindOneAndModifyDto body, String status){
    Criteria criteria = Criteria.where("checkoutId").is(body.checkoutId()).and("status").is(status.toLowerCase());
    Query query = Query.query(criteria);

    Update update = new Update();
    update.set("invoiceId", body.invoiceId());
    update.set("status", body.status());

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    MainOrderModel updatedDoc = mongoTemplate
    .findAndModify(query, update, options, MainOrderModel.class, collectionName);

    if(updatedDoc != null){
      return ResponseEntity.ok(updatedDoc);
    } else {
      String message = "Document with ID: " + body.checkoutId() + " was not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

  }

  @Transactional
  public ResponseEntity<Object> findAllByIdAndRemove(List<String> id){
    try {
      Query query = Query.query(Criteria.where("_id").in(id));
      List<MainOrderModel> removedDocument = mongoTemplate.findAllAndRemove(query, MainOrderModel.class, collectionName);

      System.out.println(removedDocument.size());
      return ResponseEntity.ok(removedDocument);
    } catch(DataAccessResourceFailureException error){
      String message = "Lost connection to Mongo database occurred processing request !";
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
  }

  public MainOrderModel getOneById(String id){
    MainOrderModel foundDoc = mongoTemplate.findById(id, MainOrderModel.class, collectionName);

    return foundDoc;
  }

  public List<MainOrderModel> getAll(){
    List<MainOrderModel> docList = mongoTemplate.findAll(MainOrderModel.class, collectionName);

    return docList;
  }

  public List<MainOrderModel> getAllBySelector(String selector, List<String> status){
    Query query = Query.query(Criteria.where(selector).in(status));

    List<MainOrderModel> foundDocs = mongoTemplate.find(query, MainOrderModel.class, collectionName);

    return foundDocs;
  }

}
