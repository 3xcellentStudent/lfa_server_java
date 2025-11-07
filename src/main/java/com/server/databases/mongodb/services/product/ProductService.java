package com.server.databases.mongodb.services.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.models.product.ProductModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.media.MediaService;

@Service
public class ProductService {

  @Autowired
  private MediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;

  private Logger logger = LoggerFactory.getLogger(ProductService.class);

  @Value("${databases.mongodb.collections.media}")
  private String mediaCollectionName;
  @Value("${databases.mongodb.collections.product.variation}")
  private String variationCollectionName;
  @Value("${databases.mongodb.collections.reviews}")
  private String reviewsCollectionName;

  public ResponseEntity<Object> createOne(ProductModel body){
    String id = UUID.nameUUIDFromBytes((body.getProductName() + "-" + body.getCollectionName()).getBytes()).toString();
    Query query = Query.query(Criteria.where("id").is(id));
    boolean isExists = mongoTemplate.exists(query, ProductModel.class, body.getCollectionName());
    
    if(isExists == true){
      return ResponseEntity.status(409).body("This object with ID: " + id + " is exists !...");
    } else {
      String mediaId = UUID.fromString(id).toString();

      long timestamp = System.currentTimeMillis();

      Object mediaServiceEntity = mediaService.createOne(mediaId, id, timestamp, mediaCollectionName + "-" + body.getCollectionName()).getBody();
    
      body.setId(id);
      body.setMediaId(mediaId);
      body.setReviewsSnapshot();
      body.setCreatedAt(timestamp);
      body.setUpdatedAt(timestamp);
      body.setProductVariationsId(List.of());
      body.setProductVariations(List.of());

      ProductModel savedObject = mongoTemplate.save(body, body.getCollectionName());
      
      savedObject.setMediaContent((MediaModel) mediaServiceEntity);

      return ResponseEntity.ok(savedObject);
    }
  }

  public ResponseEntity<Object> findManyRecursiveById(List<String> id, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));
    List<ProductModel> foundObject = mongoTemplate.find(query, ProductModel.class, collectionName);
    List<ProductModel> modifiedObject = addEntitiesToManyProductObjects(foundObject, collectionName);

    return ResponseEntity.ok(modifiedObject);
  }

  public ResponseEntity<Object> findRecursiveById(String id, String collectionName){
    // boolean isExists = mainService.entityExistingInDatabase("id", id, collectionName);

    // if(isExists){
      // return ResponseEntity.status(404).body(String.format("Document with ID \"%s\" is not exist !", id));
    // } else {
      ProductModel foundObject = mongoTemplate.findById(id, ProductModel.class, collectionName);

      if(foundObject == null){
        String message = String.format("Document with ID \"%s\" is not exists !", id);
        logger.warn(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
      } else {
        ProductModel modifiedObject = addEntitiesToOneProductObject(foundObject);
        return ResponseEntity.ok(modifiedObject);
      }
    // }
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> id, String productCollectionName){
    List<ProductModel> deletedProducts = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("id").in(id)), ProductModel.class, productCollectionName);

    List<ReviewsModel> deletedReviews = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(id)), ReviewsModel.class, reviewsCollectionName + "-" + productCollectionName
    );

    List<MediaModel> deletedMedia = mongoTemplate
    .findAllAndRemove(Query.query(
      Criteria.where("parentId").in(id)), MediaModel.class, mediaCollectionName + "-" + productCollectionName
    );

    List<ProductVariationModel> deletedProductVariations = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(id)), 
      ProductVariationModel.class, 
      variationCollectionName + "-" + productCollectionName
    );

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("products", deletedProducts);
    responseBody.put("reviews", deletedReviews);
    responseBody.put("media", deletedMedia);
    responseBody.put("productVariations", deletedProductVariations);

    return ResponseEntity.ok(responseBody);
  }

  private List<ProductModel> addEntitiesToManyProductObjects(List<ProductModel> productsList, String producCollectionName){
    List<ProductModel> modifiedProductsList = productsList.stream()
    .map(oneObject -> addEntitiesToOneProductObject(oneObject)).filter(Objects::nonNull).toList();

    return modifiedProductsList;
  }

  private ProductModel addEntitiesToOneProductObject(ProductModel product){
    // System.out.println("PRODUCT: " + product);
    MediaModel mediaObject = mongoTemplate.findById(
      product.getMediaId(), MediaModel.class, mediaCollectionName + "-" + product.getCollectionName()
    );

    Query productVariationsQuery = Query.query(
      Criteria.where("parentId").in(product.getId())
    );

    List<ProductVariationModel> productVatriations = mongoTemplate.find(
      productVariationsQuery, ProductVariationModel.class, variationCollectionName + "-" + product.getCollectionName()
    );

    product.setProductVariations(productVatriations);
    product.setMediaContent(mediaObject);
    return product;
  }

}
