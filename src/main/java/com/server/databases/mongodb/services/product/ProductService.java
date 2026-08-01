package com.server.databases.mongodb.services.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.dto.product.CreateNewProductDto;
import com.server.databases.mongodb.models.product.ProductParentModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;

@Service
public class ProductService {

  @Value("${databases.mongodb.collections.product.main}")
  private String collection;
  @Value("${databases.mongodb.collections.product.variation}")
  private String variationCollection;
  @Value("${databases.mongodb.collections.media}")
  private String mediaCollection;
  @Value("${databases.mongodb.collections.reviews}")
  private String reviewsCollection;

  @Autowired
  private MongoTemplate mongoTemplate;

  private Logger logger = LoggerFactory.getLogger(ProductService.class);

  public ResponseEntity<Object> createOne(CreateNewProductDto body){
    ProductParentModel product = new ProductParentModel(body);

    long timestamp = System.currentTimeMillis();

    product.setCreatedAt(timestamp);
    product.setUpdatedAt(timestamp);

    ProductParentModel savedObject = mongoTemplate.save(product, collection);
    
    savedObject.setMediaContent(body.mediaContent());

    return ResponseEntity.ok().body(savedObject);
  }

  public ResponseEntity<Object> findManyRecursiveById(List<String> id, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));
    List<ProductParentModel> productsList = mongoTemplate.find(query, ProductParentModel.class, collectionName);
    List<ProductParentModel> filteredObject = productsList.stream()
    .map(oneObject -> addEntitiesToOneProductObject(oneObject)).filter(Objects::nonNull).toList();

    // return ResponseEntity.ok().header(HttpHeaders.LAST_MODIFIED, HttpDateFormatter.formatLastModified(modifiedObject)).body(modifiedObject);
    return ResponseEntity.ok(filteredObject);
  }

  public ResponseEntity<Object> findRecursiveById(String id, String collectionName){
    // boolean isExists = mainService.entityExistingInDatabase("id", id, collectionName);

    // if(isExists){
      // return ResponseEntity.status(404).body(String.format("Document with ID \"%s\" is not exist !", id));
    // } else {
      ProductParentModel parentDoc = mongoTemplate.findById(id, ProductParentModel.class, collectionName);

      if(parentDoc == null){
        String message = String.format("Document with ID \"%s\" is not exists !", id);
        logger.warn(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
      } else {
        // ProductParentModel modifiedObject = addEntitiesToOneProductObject(foundObject);

        Query variationQuery = Query.query(Criteria.where("parentId").is(parentDoc.getId()));
        List<ProductVariationModel> variationDoc = mongoTemplate.find(variationQuery, ProductVariationModel.class, variationCollection);

        parentDoc.setVariations(variationDoc);

        return ResponseEntity.ok(parentDoc);
      }
    // }
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> ids, String productCollectionName){
    List<ProductParentModel> deletedProducts = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("id").in(ids)), ProductParentModel.class, productCollectionName);

    List<ReviewsModel> deletedReviews = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(ids)), ReviewsModel.class, reviewsCollection + "-" + productCollectionName
    );

    List<ProductVariationModel> deletedProductVariations = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(ids)), 
      ProductVariationModel.class, 
      variationCollection + "-" + productCollectionName
    );

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("products", deletedProducts);
    responseBody.put("reviews", deletedReviews);
    responseBody.put("productVariations", deletedProductVariations);

    return ResponseEntity.ok(responseBody);
  }

  private ProductParentModel addEntitiesToOneProductObject(ProductParentModel product){
    Query query = Query.query(Criteria.where("parentId").in(product.getId()));

    List<ProductVariationModel> variationEntities = mongoTemplate
    .find(query, ProductVariationModel.class, variationCollection);

    product.setVariations(variationEntities);
    return product;
  }

}
