package com.server.databases.mongodb.services.product.variation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.models.product.ProductModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;

@Service
public class ProductVariationService {

  private String variationCollectionNamePrefix = "variation-";

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createByParentId(ProductVariationModel body){
    String id = UUID.nameUUIDFromBytes((body.getVariationName() + "-" + body.getCollectionName()).getBytes()).toString();
    Query query = Query.query(Criteria.where("id").is(id));
    boolean isExists = mongoTemplate.exists(query, ProductVariationModel.class, body.getCollectionName());

    if(isExists == true){
      return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
    } else {
      long timestamp = System.currentTimeMillis();
    
      body.setId(id);
      body.setCreatedAt(timestamp);
      body.setUpdatedAt(timestamp);

      String parentCollectionName = body.getCollectionName().substring(variationCollectionNamePrefix.length());

      Query parentDocumentQuery = Query.query(Criteria.where("id").is(body.getParentId()));
      Update parentDocumentUpdate = new Update().push("productVariationsId").value(id);

      mongoTemplate.findAndModify(parentDocumentQuery, parentDocumentUpdate, ProductModel.class, parentCollectionName);

      ProductVariationModel savedObject = mongoTemplate.save(body, body.getCollectionName());
      
      return ResponseEntity.ok(savedObject);
    }
  }

  public ResponseEntity<Object> deteleManyById(DeleteManyById body){
    String parentCollectionName = body.getCollectionName().substring(variationCollectionNamePrefix.length());

    Query parentIsExistsQuery = Query.query(Criteria.where("id").is(body.getParentId()));
    boolean parentIsExists = mongoTemplate.exists(parentIsExistsQuery, ProductModel.class, parentCollectionName);

    if(parentIsExists){
      Query parentUpdateQuery = Query.query(Criteria.where("id").is(body.getParentId()));
      // Update parentUpdateEntity = new Update().set("productVariationsId", new ArrayList<>());
      Update parentUpdateEntity = new Update().pullAll("productVariationsId", body.getId().toArray());
      mongoTemplate.findAndModify(parentUpdateQuery, parentUpdateEntity, ProductModel.class, parentCollectionName);

      Query deleteVariationsQuery = Query.query(Criteria.where("id").in(body.getId()));

      List<ProductVariationModel> deletedProductVariations = mongoTemplate
      .findAllAndRemove(deleteVariationsQuery, ProductVariationModel.class, body.getCollectionName());

      return ResponseEntity.ok(deletedProductVariations);
    } else {
      String message = "Parent document with ID: \"" + body.getParentId() + "\" not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
  }

}
