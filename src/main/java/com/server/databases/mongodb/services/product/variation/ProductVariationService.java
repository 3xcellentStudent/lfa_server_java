package com.server.databases.mongodb.services.product.variation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.bulk.BulkWriteResult;
import com.server.databases.mongodb.dto.main.DeleteManyById;
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

@Service
public class ProductVariationService {

  // private final String variationCollectionNameHead = "variation-";
  
  private final String stockAmountAvailableKey = "stockInfo.stockAmountAvailable";
  private final String stockAmountReservedKey = "stockInfo.stockAmountReserved";

  private Logger logger = LoggerFactory.getLogger(ProductVariationService.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createByParentId(CreateVariationByParentId body){
    // String id = UUID.randomUUID().toString();
    // Query query = Query.query(Criteria.where("id").is(id));
    // boolean isExists = mongoTemplate.exists(query, ProductVariationModel.class, body.getCollectionName());

    // if(isExists == true){
    //   return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
    // } else {
      long timestamp = System.currentTimeMillis();

      ProductVariationModel newVariation = new ProductVariationModel(body);
    
      // newVariation.setId(id);
      newVariation.setCreatedAt(timestamp);
      newVariation.setUpdatedAt(timestamp);

      // String parentCollectionName = body.getCollectionName().substring(variationCollectionNameHead.length());

      // Query parentDocumentQuery = Query.query(Criteria.where("id").is(body.getParentId()));
      // Update parentDocumentUpdate = new Update();
      // parentDocumentUpdate.push("productVariationsId").value(id);
      // parentDocumentUpdate.set("updatedAt", timestamp);

      // mongoTemplate.findAndModify(parentDocumentQuery, parentDocumentUpdate, ProductModel.class, parentCollectionName);

      ProductVariationModel savedObject = mongoTemplate.insert(newVariation, newVariation.getCollectionName());
      
      return ResponseEntity.ok(savedObject);
    // }
  }

  public void inventoryUpdateBulk(){
    
  }

  // public ResponseEntity<Object> updateOneById(UpdateVariationById body){
  //   Query variationCollectionQuery = Query.query(Criteria.where("_id").is(body.getId()));
  //   boolean isExists = mongoTemplate.exists(variationCollectionQuery, body.getCollectionName());

  //   if(isExists){
  //     long timestamp = System.currentTimeMillis();

  //     Update variationDocUpdate = new Update();
  //     variationDocUpdate.set(body.getField(), body.getNewData());
  //     variationDocUpdate.set("updatedAt", timestamp);

  //     UpdateResult updatedVariationDoc = mongoTemplate
  //     .updateFirst(variationCollectionQuery, variationDocUpdate, ProductVariationModel.class, body.getCollectionName());

  //     return ResponseEntity.ok(updatedVariationDoc);
  //   } else {
  //     String message = "Variation entity with ID: " + body.getId() + " was not founded in database !";
  //     logger.error(message);
  //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  //   }
  // }

  // public void bulkOpsInventoryUpdate(List<CheckoutCreateSessionClientRequestDto> cart, List<ProductVariationModel> validatedArray){
  //   Map<String, ProductVariationModel> validatedArrayMapById = validatedArray.stream()
  //   .collect(Collectors.toMap(ProductVariationModel::getId, entity -> entity));

  //   Map<String, List<CheckoutCreateSessionClientRequestDto>> cartMapByCollName = cart.stream()
  //   .collect(Collectors.groupingBy(CheckoutCreateSessionClientRequestDto::collectionName));

  //   cartMapByCollName.forEach((collectionName, items) -> {
  //     BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, ProductVariationModel.class, collectionName);

  //     items.forEach(entity -> {
  //       ProductVariationModel variationEntity = validatedArrayMapById.get(entity.productId());
  //       Query query = Query.query(Criteria.where("_id").is(entity.productId()));
  
  //       Update update = new Update();
  //       update.set(stockAmountAvailableKey, variationEntity.getStockInfo().getStockAmountAvailable() - entity.quantity());
  //       update.set(stockAmountReservedKey, variationEntity.getStockInfo().getStockAmountReserved() + entity.quantity());
  
  //       bulkOps.updateOne(query, update);
  //     });

  //     BulkWriteResult result = bulkOps.execute();

  //     logger.info("The result of collection " + collectionName + ": " + result);
  //   });
    
  //   return;
  // }


  public void bulkOpsInventoryUpdate(List<CheckoutCreateSessionClientRequestDto> cart){
    // Map<String, ProductVariationModel> validatedArrayMapById = validatedArray.stream()
    // .collect(Collectors.toMap(ProductVariationModel::getId, entity -> entity));

    Map<String, List<CheckoutCreateSessionClientRequestDto>> cartMapByCollName = cart.stream()
    .collect(Collectors.groupingBy(CheckoutCreateSessionClientRequestDto::collectionName));

    cartMapByCollName.forEach((collectionName, items) -> {
      BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, ProductVariationModel.class, collectionName);

      items.forEach(entity -> {
        // ProductVariationModel variationEntity = validatedArrayMapById.get(entity.productId());
        Query query = Query.query(Criteria.where("_id").is(entity.productId()));
  
        Update update = new Update();
        update.inc("stockInfo.stockAmountAvailable", -entity.quantity());
        update.inc("stockInfo.stockAmountReserved", entity.quantity());
  
        bulkOps.updateOne(query, update);
      });

      BulkWriteResult result = bulkOps.execute();

      logger.info("The result of collection " + collectionName + ": " + result);
    });
    
    return;
  }


  public ResponseEntity<Object> deteleManyById(DeleteManyById body){
    // String parentCollectionName = body.getCollectionName().substring(variationCollectionNameHead.length());

    // Query parentIsExistsQuery = Query.query(Criteria.where("id").is(body.getParentId()));
    // boolean parentIsExists = mongoTemplate.exists(parentIsExistsQuery, ProductParentModel.class, parentCollectionName);

    // if(parentIsExists){
      Query deletedVariationsQuery = Query.query(Criteria.where("id").in(body.getId()));

      List<ProductVariationModel> deletedVariationsEntities = mongoTemplate
      .findAllAndRemove(deletedVariationsQuery, ProductVariationModel.class, body.getCollectionName());

      return ResponseEntity.ok(deletedVariationsEntities);
    // } else {
    //   String message = "Parent document with ID: \"" + body.getParentId() + "\" not found !";
    //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    // }
  }

}
