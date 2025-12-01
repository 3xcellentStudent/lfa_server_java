package com.server.databases.mongodb.services.product.variation;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.server.databases.mongodb.dto.product.variation.UpdateVariationById;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;

@Service
public class ProductVariationUpdateService {

  private Logger logger = LoggerFactory.getLogger(ProductVariationUpdateService.class);

  private String variationCollectionNameHead = "variation-";

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public <T> ResponseEntity<Object> updateOneById(UpdateVariationById body, Class<T> parentClass){
    Query variationCollectionQuery = Query.query(Criteria.where("id").is(body.getId()));
    boolean isExists = mongoTemplate.exists(variationCollectionQuery, ProductVariationModel.class, body.getCollectionName());

    if(isExists){
      long timestamp = System.currentTimeMillis();

      Update variationDocUpdate = new Update();
      variationDocUpdate.set(body.getField(), body.getNewData());
      variationDocUpdate.set("updatedAt", timestamp);

      UpdateResult updatedVariationDoc = mongoTemplate
      .updateFirst(variationCollectionQuery, variationDocUpdate, ProductVariationModel.class, body.getCollectionName());

      String parentCollectionName = body.getCollectionName().substring(variationCollectionNameHead.length());

      Query parentDocmentQuery = Query.query(Criteria.where("id").is(body.getParentId()));
      UpdateResult updatedParentDoc = mongoTemplate
      .updateFirst(parentDocmentQuery, new Update().set("updatedAt", timestamp), parentClass, parentCollectionName);
      
      return createResponse(updatedVariationDoc, updatedParentDoc);
    } else {
      String message = String.format("Document with ID %s not found !", body.getId());
      logger.warn(message);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
  }

  private ResponseEntity<Object> createResponse(UpdateResult updatedVariationDoc, UpdateResult updatedParentDoc){
    if(updatedVariationDoc.getModifiedCount() > 0 && updatedParentDoc.getModifiedCount() > 0){
      HashMap<String, Object> responseObject = new HashMap<>();
      responseObject.put("variationDocResult", updatedVariationDoc);
      responseObject.put("parentDocResult", updatedParentDoc);
      
      logger.info("VariationDoc and his ParentDoc have been successfuly updated !");
      return ResponseEntity.ok(responseObject);
    } else {
      String message = "Document modification failed !";
      logger.warn(message);
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(message);
    }
  }

}
