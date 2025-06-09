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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.media.diffusers.DiffusersMediaService;
import com.server.databases.mongodb.services.uuid.CustomUUID;

@Service
public class ProductsService {

  @Autowired
  private DiffusersMediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Value("${mongodb.collections.products.diffusers}")
  private String collectionName;

  public ResponseEntity<Object> createOne(ProductsModel productObject, String collectionName){
    try {
      String id = CustomUUID.fromString(new String[] {productObject.getTitle(), productObject.getStockInfo().category});
      Query query = Query.query(Criteria.where("id").is(id));
      boolean isExists = mongoTemplate.exists(query, ProductsModel.class, collectionName);
      
      if(isExists == false){
        String mediaId = CustomUUID.fromString(id);

        long timestamp = System.currentTimeMillis();
  
        Object mediaServiceEntity = mediaService.createOne(mediaId, id, timestamp).getBody();
      
        productObject.setId(id);
        productObject.setMediaId(mediaId);
        productObject.setCreatedAt(timestamp);
        productObject.setUpdatedAt(timestamp);

        ProductsModel savedProduct = mongoTemplate.save(productObject, collectionName);
        
        savedProduct.setMediaContent((DiffusersMediaModel) mediaServiceEntity);

        String response = objectMapper.writeValueAsString(savedProduct);

        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
      }
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public ResponseEntity<Object> findAllRecursiveById(List<String> id, String collectionName){
    Query query = Query.query(Criteria.where("id").in(id));
    List<ProductsModel> foundProducts = mongoTemplate
    .find(query, ProductsModel.class, collectionName);
    List<ProductsModel> modifiedProducts = modifyProducts(foundProducts);

    return ResponseEntity.ok(modifiedProducts);
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> id){
    try {
      List<ProductsModel> removedProducts = mongoTemplate
      .findAllAndRemove(Query.query(Criteria.where("id").in(id)), ProductsModel.class);
      List<DiffusersReviewsModel> removedReviews = mongoTemplate
      .findAllAndRemove(Query.query(Criteria.where("parentId").in(id)), DiffusersReviewsModel.class);
      List<DiffusersMediaModel> removedMedia = mongoTemplate
      .findAllAndRemove(Query.query(Criteria.where("parentId").in(id)), DiffusersMediaModel.class);

      Map<String, Object> jsonBody = new HashMap<>();
      jsonBody.put("products", removedProducts);
      jsonBody.put("reviews", removedReviews);
      jsonBody.put("media", removedMedia);

      String response = objectMapper.writeValueAsString(jsonBody);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Internal server error in class " + this.getClass().getName());
    }
  }

  private List<ProductsModel> modifyProducts(List<ProductsModel> productsList){
    try {
      List<ProductsModel> modifiedProductsList = productsList.stream()
      .map(oneObject -> {
        // List<DiffusersReviewsModel> reviewsList = mainService
        // .findAllById("parentId", oneObject.getId(), DiffusersReviewsModel.class);

        DiffusersMediaModel mediaObject = mongoTemplate.findById(oneObject.getMediaId(), DiffusersMediaModel.class);

        // oneObject.setReviews(reviewsList);
        oneObject.setMediaContent(mediaObject);
        return oneObject;
      }).filter(Objects::nonNull).toList();

      return modifiedProductsList;
    } catch (Exception error) {
      error.printStackTrace();
      System.err.println("Internal server error in class: " + this.getClass().getName());
      return null;
    }
  }

}
