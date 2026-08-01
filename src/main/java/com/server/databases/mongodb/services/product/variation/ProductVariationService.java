package com.server.databases.mongodb.services.product.variation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.bulk.BulkWriteResult;
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

@Service
public class ProductVariationService {

  private Logger logger = LoggerFactory.getLogger(ProductVariationService.class);

  @Value("${databases.mongodb.collections.product.variation}")
  private String collection;

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createByParentId(CreateVariationByParentId body){
    long timestamp = System.currentTimeMillis();

    ProductVariationModel model = new ProductVariationModel(body, collection);
  
    model.setCreatedAt(timestamp);
    model.setUpdatedAt(timestamp);

    ProductVariationModel insertedDocument = mongoTemplate.insert(model, collection);
    
    return ResponseEntity.ok(insertedDocument);
  }

  // public void bulkOpsInventoryUpdate(List<CheckoutCreateSessionClientRequestDto> cart){
  //   Map<String, List<CheckoutCreateSessionClientRequestDto>> cartMapByCollName = cart.stream()
  //   .collect(Collectors.groupingBy(CheckoutCreateSessionClientRequestDto::collectionName));

  //   cartMapByCollName.forEach((collectionName, items) -> {
  //     BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, ProductVariationModel.class, collectionName);

  //     items.forEach(entity -> {
  //       // ProductVariationModel variationEntity = validatedArrayMapById.get(entity.productId());
  //       Query query = Query.query(Criteria.where("_id").is(entity.productId()));
  
  //       Update update = new Update();
  //       update.inc("stockInfo.stockAmountAvailable", -entity.quantity());
  //       update.inc("stockInfo.stockAmountReserved", entity.quantity());
  
  //       bulkOps.updateOne(query, update);
  //     });

  //     BulkWriteResult result = bulkOps.execute();

  //     logger.info("The result of collection " + collectionName + ": " + result);
  //   });
    
  //   return;
  // }

  public Integer bulkOpsInventoryUpdate(List<CheckoutCreateSessionClientRequestDto> cart){
    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, ProductVariationModel.class, collection);
    
    cart.forEach(entity -> {
      Query query = Query.query(Criteria.where("_id").is(entity.productId()));

      Update update = new Update();
      update.inc("stockInfo.stockAmountAvailable", -entity.quantity());
      update.inc("stockInfo.stockAmountReserved", entity.quantity());

      bulkOps.updateOne(query, update);
    });

    BulkWriteResult result = bulkOps.execute();
    logger.info("The result of collection " + collection + ": " + result);
    
    return result.getModifiedCount();
  }

  public ResponseEntity<Object> deteleManyById(List<String> ids){
    Query deletedVariationsQuery = Query.query(Criteria.where("id").in(ids));

    List<ProductVariationModel> deletedVariationsEntities = mongoTemplate
    .findAllAndRemove(deletedVariationsQuery, ProductVariationModel.class, collection);

    return ResponseEntity.ok(deletedVariationsEntities);
  }

}
