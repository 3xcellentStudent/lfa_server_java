package com.server.databases.mongodb.services.product.variation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.product.ProductModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;

@Service
public class ProductVariationService {

  private String variationCollectionNamePrefix = "variation-";

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createByParentId(ProductVariationModel body){
    String id = UUID.nameUUIDFromBytes((body.getVariationName() + "-" + body.getCollectionName()).getBytes()).toString();
    Query documentQuery = Query.query(Criteria.where("id").is(id));
    boolean isExists = mongoTemplate.exists(documentQuery, ProductVariationModel.class, body.getCollectionName());

    if(isExists == true){
      return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
    } else {
      long timestamp = System.currentTimeMillis();
    
      body.setId(id);
      body.setCreatedAt(timestamp);
      body.setUpdatedAt(timestamp);

      String parentCollectionName = body.getCollectionName().substring(variationCollectionNamePrefix.length());

      Query parentDocumentQuery = Query.query(Criteria.where("id").is(body.getParentId()));
      Update parentDocumentUpdate = new Update().push("productVariationsIds").value(id);

      mongoTemplate.findAndModify(parentDocumentQuery, parentDocumentUpdate, ProductModel.class, parentCollectionName);

      ProductVariationModel savedObject = mongoTemplate.save(body, body.getCollectionName());
      
      return ResponseEntity.ok(savedObject);
    }
  }

}
