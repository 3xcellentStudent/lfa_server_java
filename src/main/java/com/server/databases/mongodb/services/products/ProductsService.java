package com.server.databases.mongodb.services.products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.media.MediaService;
import com.server.databases.mongodb.services.uuid.CustomUUID;

@Service
public class ProductsService {

  @Autowired
  private MediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> createOne(ProductsModel body, String collectionName){
    String id = CustomUUID.fromString(new String[] {body.getTitle(), body.getStockInfo().category});
    Query query = Query.query(Criteria.where("id").is(id));
    boolean isExists = mongoTemplate.exists(query, ProductsModel.class, collectionName);
    
    if(isExists == false){
      String mediaId = CustomUUID.fromString(id);

      long timestamp = System.currentTimeMillis();

      Object mediaServiceEntity = mediaService.createOne(mediaId, id, timestamp, "media-" + collectionName).getBody();
    
      body.setId(id);
      body.setMediaId(mediaId);
      body.setCreatedAt(timestamp);
      body.setUpdatedAt(timestamp);

      ProductsModel savedProduct = mongoTemplate.save(body, collectionName);
      
      savedProduct.setMediaContent((MediaModel) mediaServiceEntity);

      return ResponseEntity.ok(savedProduct);
    } else {
      return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
    }
  }

  public ResponseEntity<Object> findAllRecursiveById(List<String> id, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));
    List<ProductsModel> foundProducts = mongoTemplate.find(query, ProductsModel.class, collectionName);
    List<ProductsModel> modifiedProducts = modifyMediaOfProduct(foundProducts, collectionName);

    return ResponseEntity.ok(modifiedProducts);
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> id, String productCollectionName){
    List<ProductsModel> removedProducts = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("id").in(id)), ProductsModel.class, productCollectionName);
    List<ReviewsModel> removedReviews = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("parentId").in(id)), ReviewsModel.class, "reviews " + productCollectionName);
    List<MediaModel> removedMedia = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("parentId").in(id)), MediaModel.class, "media " + productCollectionName);

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("products", removedProducts);
    responseBody.put("reviews", removedReviews);
    responseBody.put("media", removedMedia);

    return ResponseEntity.ok(responseBody);
  }

  private List<ProductsModel> modifyMediaOfProduct(List<ProductsModel> productsList, String producCollectionName){
    List<ProductsModel> modifiedProductsList = productsList.stream().map(oneObject -> {
      MediaModel mediaObject = mongoTemplate.findById(oneObject.getMediaId(), MediaModel.class, "media " + producCollectionName);

      oneObject.setMediaContent(mediaObject);
      return oneObject;
    }).filter(Objects::nonNull).toList();

    return modifiedProductsList;
  }

}
