package com.server.databases.mongodb.services.orders;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.bulk.BulkWriteResult;
import com.server.common.api.exceptions.mongo.ResourceNotFoundException;
import com.server.common.dto.stripe.orders.OrdersFindOneAndModifyDto;
import com.server.common.types.stripe.orders.OrdersStatusesType;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@Service
public class OrdersService {

  @Value("${databases.mongodb.collections.orders}")
  private String ordersCollection;

  private Logger logger = LoggerFactory.getLogger(OrdersService.class); 

  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> create(MainOrderModel body){
    MainOrderModel createdDocument = mongoTemplate.insert(body, ordersCollection);

    return ResponseEntity.ok(createdDocument);
  }

  @Transactional
  public ResponseEntity<Object> updateOneById(OrdersFindOneAndModifyDto body, String status){
    Criteria criteria = Criteria.where("checkoutId").is(body.checkoutId()).and("status").is(OrdersStatusesType.valueOf(status.toUpperCase()).name());
    Query query = Query.query(criteria);

    Update update = new Update();
    update.set("invoiceId", body.invoiceId());
    update.set("status", OrdersStatusesType.valueOf(body.status().toUpperCase()).name());

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    MainOrderModel updatedDoc = mongoTemplate
    .findAndModify(query, update, options, MainOrderModel.class, ordersCollection);

    if(updatedDoc != null){
      logger.info("Expired document ID \"" + body.checkoutId() + "\" was successfully updated !");
      return ResponseEntity.ok(updatedDoc);
    } else {
      String message = "Document ID: " + body.checkoutId() + " was not found !";
      throw new ResourceNotFoundException(message);
    }
  }

  @Transactional
  public ResponseEntity<Object> bulkUpdate(List<StripeCheckoutExpiredDto> sessionsList){
    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, MainOrderModel.class, ordersCollection);
    
    sessionsList.forEach(entity -> {
      Query query = Query.query(Criteria.where("_id").is(entity.id()));
      
      Update update = new Update();
      update.set("status", OrdersStatusesType.valueOf(entity.status().toUpperCase()).name());
      
      bulkOps.updateOne(query, update);
    });
    
    BulkWriteResult result = bulkOps.execute();

    return ResponseEntity.ok(result.getMatchedCount());
  }

  public ResponseEntity<Object> findAllByIdAndRemove(List<String> id){
    Query query = Query.query(Criteria.where("_id").in(id));
    List<MainOrderModel> removedDocument = mongoTemplate.findAllAndRemove(query, MainOrderModel.class, ordersCollection);

    return ResponseEntity.ok(removedDocument);
  }

  public MainOrderModel getOneById(String id){
    MainOrderModel foundDoc = mongoTemplate.findById(id, MainOrderModel.class, ordersCollection);
    return foundDoc;
  }

  public List<MainOrderModel> getAll(){
    List<MainOrderModel> docList = mongoTemplate.findAll(MainOrderModel.class, ordersCollection);
    return docList;
  }

  public List<MainOrderModel> getAllBySelector(String selector, List<String> status){
    Query query = Query.query(Criteria.where(selector).in(status));

    List<MainOrderModel> foundDocs = mongoTemplate.find(query, MainOrderModel.class, ordersCollection);

    return foundDocs;
  }

}
