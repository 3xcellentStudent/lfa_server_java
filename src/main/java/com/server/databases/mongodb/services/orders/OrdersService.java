package com.server.databases.mongodb.services.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.bulk.BulkWriteResult;
import com.server.common.dto.stripe.orders.OrdersFindOneAndModifyDto;
import com.server.common.types.stripe.orders.OrdersStatusesType;
import com.server.databases.mongodb.models.orders.OrderModel;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@Service
public class OrdersService {

  @Value("${databases.mongodb.collections.orders}")
  private String ordersCollection;

  @Autowired
  private MongoTemplate mongoTemplate;

  public OrderModel create(OrderModel body){
    return mongoTemplate.insert(body, ordersCollection);
  }

  public OrderModel updateOneById(OrdersFindOneAndModifyDto body, String status){
    Criteria criteria = Criteria.where("checkoutId").is(body.checkoutId()).and("status").is(OrdersStatusesType.valueOf(status.toUpperCase()).name());
    Query query = Query.query(criteria);

    Update update = new Update();
    update.set("invoiceId", body.invoiceId());
    update.set("status", OrdersStatusesType.valueOf(body.status().toUpperCase()).name());

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

    OrderModel updatedDoc = mongoTemplate
    .findAndModify(query, update, options, OrderModel.class, ordersCollection);

    return updatedDoc;
  }

  @Transactional
  public Integer bulkUpdate(List<StripeCheckoutExpiredDto> sessionsList){
    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, OrderModel.class, ordersCollection);
    
    sessionsList.forEach(entity -> {
      Query query = Query.query(Criteria.where("_id").is(entity.id()));
      
      Update update = new Update();
      update.set("status", OrdersStatusesType.valueOf(entity.status().toUpperCase()).name());
      
      bulkOps.updateOne(query, update);
    });
    
    BulkWriteResult result = bulkOps.execute();

    return result.getMatchedCount();
  }

  public List<OrderModel> findAllByIdAndRemove(List<String> id){
    Query query = Query.query(Criteria.where("_id").in(id));
    List<OrderModel> removedDocument = mongoTemplate.findAllAndRemove(query, OrderModel.class, ordersCollection);

    return removedDocument;
  }

  public OrderModel getOneById(String id){
    OrderModel foundDoc = mongoTemplate.findById(id, OrderModel.class, ordersCollection);
    return foundDoc;
  }

  public List<OrderModel> getOpenOrders(String selector, List<String> status){
    Query query = Query.query(Criteria.where(selector).in(status));

    List<OrderModel> foundDocs = mongoTemplate.find(query, OrderModel.class, ordersCollection);

    return foundDocs;
  }

}
