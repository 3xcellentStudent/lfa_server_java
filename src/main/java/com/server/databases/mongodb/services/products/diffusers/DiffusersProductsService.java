package com.server.databases.mongodb.services.products.diffusers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;
import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.databases.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.MainService;
import com.server.databases.mongodb.services.media.diffusers.DiffusersMediaService;
import com.server.databases.mongodb.services.uuid.CustomUUID;

@Service
public class DiffusersProductsService {

  @Autowired
  private DiffusersMediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MainService mainService;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createOne(String requestBodyString){
    try {
      DiffusersProductsModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersProductsModel.class);
      String id = CustomUUID.fromString(new String[] {requestBodyObject.title, requestBodyObject.stockInfo.category});
      boolean isExists = mongoTemplate.exists(QueriesHelper.getId("id", id), DiffusersProductsModel.class);
      
      if(isExists == false){
        String mediaId = CustomUUID.fromString(id);

        long timestamp = System.currentTimeMillis();
  
        Object mediaServiceEntity = mediaService.createOne(mediaId, id, timestamp).getBody();
      
        requestBodyObject.setId(id);
        requestBodyObject.setMediaId(mediaId);
        requestBodyObject.setCreateTime(timestamp);
        requestBodyObject.setUpdateTime(timestamp);

        DiffusersProductsModel savedProduct = mongoTemplate.save(requestBodyObject);
        
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

  public ResponseEntity<Object> findAllRecursive(List<String> id){
    try {
      List<DiffusersProductsModel> foundProducts = mongoTemplate
      .find(QueriesHelper.getId("id", id), DiffusersProductsModel.class);
      List<DiffusersProductsModel> modifiedProducts = modifyProducts(foundProducts);

      String response = objectMapper.writeValueAsString(modifiedProducts);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error in class " + this.getClass().getName());
    }
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> id){
    try {
      List<DiffusersProductsModel> removedProducts = mongoTemplate
      .findAllAndRemove(QueriesHelper.getId("id", id), DiffusersProductsModel.class);
      List<DiffusersReviewsModel> removedReviews = mongoTemplate
      .findAllAndRemove(QueriesHelper.getId("parentId", id), DiffusersReviewsModel.class);
      List<DiffusersMediaModel> removedMedia = mongoTemplate
      .findAllAndRemove(QueriesHelper.getId("parentId", id), DiffusersMediaModel.class);

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

  private List<DiffusersProductsModel> modifyProducts(List<DiffusersProductsModel> productsList){
    try {
      List<DiffusersProductsModel> modifiedProductsList = productsList.stream()
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
