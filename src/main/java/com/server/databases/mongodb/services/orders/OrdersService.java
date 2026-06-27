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

import com.mongodb.DuplicateKeyException;
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
    // try {
      MainOrderModel createdDocument = mongoTemplate.insert(body, collectionName);
  
      return ResponseEntity.ok(createdDocument);

      // ###### НАСТРОИТЬ ГЛОБАЛЬНУЮ ОБРАБОТКУ ОШИБОК

    // } catch(DataAccessResourceFailureException error){
    //   String message = "Lost connection to Mongo database occurred processing request !";
    //   return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    // } catch(DuplicateKeyException error){
    //   String message = "Document with ID: " + body.getCheckoutId() + " already exist in database !";
    //   return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    // }
  }

  public ResponseEntity<Object> updateOneById(OrdersFindOneAndModifyDto body, String status){
    Criteria criteria = Criteria.where(checkoutIdKey).is(body.getCheckoutId()).and("status").in(status);
    Query query = Query.query(criteria);

    Update update = new Update();
    update.set(invoiceIdKey, body.getInvoiceId());
    update.set("status", body.getStatus());

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    MainOrderModel updatedDoc = mongoTemplate
    .findAndModify(query, update, options, MainOrderModel.class, collectionName);

    // System.out.println("Status after expire: " + updatedDoc.getStatus());

    if(updatedDoc != null){
      return ResponseEntity.ok(updatedDoc);
    } else {
      String message = "Document with ID: " + body.getCheckoutId() + " was not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

  }

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
