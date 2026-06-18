package com.server.databases.mongodb.services.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoSocketOpenException;
import com.server.databases.mongodb.dto.orders.request.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.dto.orders.request.OrdersGetOneByIdDto;
import com.server.databases.mongodb.models.orders.MainOrderModel;

@Service
public class OrdersService {
  private final String invoiceIdKey = "invoiceId";
  private final String checkoutIdKey = "checkoutId";

  @Value("${databases.mongodb.collections.orders}")
  private String collectionName;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> create(MainOrderModel body){
    try {
      MainOrderModel createdDocument = mongoTemplate.save(body, collectionName);
  
      return ResponseEntity.ok(createdDocument);
    } catch(MongoSocketOpenException error){
      String message = "Lost connection to Mongo database occurred processing request !";
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
  }

  public ResponseEntity<Object> updateOneById(OrdersFindOneAndModifyDto body){
    Query query = Query.query(Criteria.where(checkoutIdKey).is(body.getCheckoutId()));

    Update update = new Update()
    .set(invoiceIdKey, body.getInvoiceId());

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    MainOrderModel updatedDoc = mongoTemplate
    .findAndModify(query, update, options, MainOrderModel.class, collectionName);

    if(updatedDoc != null){
      return ResponseEntity.ok(updatedDoc);
    }

    String message = "Document with ID: " + body.getCheckoutId() + " was not found !";
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

  public ResponseEntity<Object> removeOneById(String id){
    try {
      Query query = Query.query(Criteria.where("id").is(id));
      MainOrderModel removedDocument = mongoTemplate.findAndRemove(query, MainOrderModel.class);

      return ResponseEntity.ok(removedDocument);
    } catch(MongoSocketOpenException error){
      String message = "Lost connection to Mongo database occurred processing request !";
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
  }

  public ResponseEntity<Object> getOneById(OrdersGetOneByIdDto body){
    MainOrderModel foundedDoc = mongoTemplate.findById(body.getCheckoutId(), MainOrderModel.class, collectionName);

    if(foundedDoc == null){
      String message = "Document with ID: " + body.getCheckoutId() + " was not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    
    return ResponseEntity.ok(foundedDoc);
  }

  public ResponseEntity<Object> getAll(){
    List<MainOrderModel> docList = mongoTemplate.findAll(MainOrderModel.class, collectionName);

    return ResponseEntity.ok(docList);
  }

}
