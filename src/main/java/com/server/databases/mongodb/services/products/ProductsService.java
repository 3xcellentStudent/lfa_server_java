package com.server.databases.mongodb.services.products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.products.variations.ProductVariations;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.media.MediaService;
import com.server.databases.mongodb.services.uuid.CustomUUID;

@Service
public class ProductsService {

  @Autowired
  private MediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Value("${mongodb.collections.media}")
  private String mediaCollectionName;
  @Value("${mongodb.collections.product.variation}")
  private String variationCollectionName;
  @Value("${mongodb.collections.reviews}")
  private String reviewsCollectionName;

  public ResponseEntity<Object> createOne(ProductsModel body){
    String id = CustomUUID.fromString(new String[] {body.getTitle(), body.getCollectionName()});
    Query query = Query.query(Criteria.where("id").is(id));
    boolean isExists = mongoTemplate.exists(query, ProductsModel.class, body.getCollectionName());
    
    if(isExists == false){
      String mediaId = CustomUUID.fromString(id);

      long timestamp = System.currentTimeMillis();

      Object mediaServiceEntity = mediaService.createOne(mediaId, id, timestamp, mediaCollectionName + "-" + body.getCollectionName()).getBody();
    
      body.setId(id);
      body.setMediaId(mediaId);
      body.setCreatedAt(timestamp);
      body.setUpdatedAt(timestamp);

      ProductsModel savedObject = mongoTemplate.save(body, body.getCollectionName());
      
      savedObject.setMediaContent((MediaModel) mediaServiceEntity);

      return ResponseEntity.ok(savedObject);
    } else {
      return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
    }
  }

  public ResponseEntity<Object> findManyRecursiveById(List<String> id, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));
    List<ProductsModel> foundObject = mongoTemplate.find(query, ProductsModel.class, collectionName);
    List<ProductsModel> modifiedObject = addEntitiesToManyProductObjects(foundObject, collectionName);

    return ResponseEntity.ok(modifiedObject);
  }

  public ResponseEntity<Object> findRecursiveById(String id, String collectionName){
    ProductsModel foundObject = mongoTemplate.findById(id, ProductsModel.class, collectionName);

    ProductsModel modifiedObject = addEntitiesToOneProductObject(foundObject);

    return ResponseEntity.ok(modifiedObject);
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> id, String productCollectionName){
    List<ProductsModel> deletedProducts = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("id").in(id)), ProductsModel.class, productCollectionName);
    List<ReviewsModel> deletedReviews = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(id)), ReviewsModel.class, reviewsCollectionName + "-" + productCollectionName
    );
    List<MediaModel> deletedMedia = mongoTemplate
    .findAllAndRemove(Query.query(
      Criteria.where("parentId").in(id)), MediaModel.class, mediaCollectionName + "-" + productCollectionName
    );
    List<ProductVariations> deletedProductVariations = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(id)), 
      ProductVariations.class, 
      variationCollectionName + "-" + productCollectionName
      );

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("products", deletedProducts);
    responseBody.put("reviews", deletedReviews);
    responseBody.put("media", deletedMedia);
    responseBody.put("productVariations", deletedProductVariations);

    return ResponseEntity.ok(responseBody);
  }

  private List<ProductsModel> addEntitiesToManyProductObjects(List<ProductsModel> productsList, String producCollectionName){
    List<ProductsModel> modifiedProductsList = productsList.stream()
    .map(oneObject -> addEntitiesToOneProductObject(oneObject)).filter(Objects::nonNull).toList();

    return modifiedProductsList;
  }

  private ProductsModel addEntitiesToOneProductObject(ProductsModel product){
    MediaModel mediaObject = mongoTemplate.findById(
      product.getMediaId(), MediaModel.class, mediaCollectionName + "-" + product.getCollectionName()
    );

    Query productVariationsQuery = Query.query(
      Criteria.where("parentId").in(product.getId())
    );

    List<ProductVariations> productVatriations = mongoTemplate.find(
      productVariationsQuery, ProductVariations.class, variationCollectionName + "-" + product.getCollectionName()
    );

    product.setProductVariations(productVatriations);
    product.setMediaContent(mediaObject);
    return product;
  }

}
