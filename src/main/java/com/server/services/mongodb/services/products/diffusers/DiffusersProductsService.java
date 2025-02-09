package com.server.services.mongodb.services.products.diffusers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.helpers.queries.QueriesHelper;
import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.services.mongodb.services.media.diffusers.DiffusersMediaService;
import com.server.services.mongodb.services.reviews.diffusers.DiffusersReviewsService;
import com.server.services.mongodb.services.uuid.CustomUUID;

@Service
public class DiffusersProductsService {

  @Autowired
  private DiffusersReviewsService reviewsService;
  @Autowired
  private DiffusersMediaService mediaService;
  @Autowired
  private MongoTemplate mongoTemplate;

  private ObjectMapper objectMapper = new ObjectMapper();

  public ResponseEntity<Object> createOne(String requestBodyString){
    try {
      DiffusersProductsModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersProductsModel.class);
      String id = CustomUUID.fromString(new String[] {requestBodyObject.title, requestBodyObject.stockInfo.category});
      boolean isExists = mongoTemplate.exists(QueriesHelper.getId("id", id), DiffusersProductsModel.class);
      
      if(isExists == false){
        String reviewsId = CustomUUID.fromString(id);
        String mediaId = CustomUUID.fromString(id);

        long timestamp = System.currentTimeMillis();
  
        Object reviewsServiceEntity = reviewsService.createOne(reviewsId, id, mediaId, timestamp).getBody();
        Object mediaServiceEntity = mediaService.createOne(mediaId, id, reviewsId, timestamp).getBody();
      
        requestBodyObject.setId(id);
        requestBodyObject.setReviewsId(reviewsId);
        requestBodyObject.setMediaId(mediaId);
        requestBodyObject.setCreateTime(timestamp);
        requestBodyObject.setUpdateTime(timestamp);

        DiffusersProductsModel savedProduct = mongoTemplate.save(requestBodyObject);
        
        savedProduct.setReviews((DiffusersReviewsModel) reviewsServiceEntity);
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

  public ResponseEntity<Object> findAllRecursive(List<String> id, int fromIndex, int toIndex){
    try {
      List<DiffusersProductsModel> foundProducts = mongoTemplate
      .find(QueriesHelper.getId("id", id), DiffusersProductsModel.class);
      List<DiffusersProductsModel> modifiedProducts = modifyProducts(foundProducts, fromIndex, toIndex);

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

  private List<DiffusersProductsModel> modifyProducts(List<DiffusersProductsModel> productsList, int fromIndex, int toIndex){
    try {
      List<DiffusersProductsModel> modifiedProductsList = productsList.stream()
      .map(oneObject -> {
        DiffusersReviewsModel reviewsModifiedEntity = reviewsService.findOneAndSliceReviewsList(oneObject.getReviewsId(), fromIndex, toIndex);
        // DiffusersReviewsModel reviewsModifiedObject = objectMapper
        // .convertValue(reviewsModifiedEntity, DiffusersReviewsModel.class);

        DiffusersMediaModel mediaObject = mongoTemplate.findById(oneObject.getMediaId(), DiffusersMediaModel.class);

        oneObject.setReviews(reviewsModifiedEntity);
        oneObject.setMediaContent(mediaObject);
        return oneObject;
      }).filter(Objects::nonNull).toList();

      return modifiedProductsList;
    } catch (Exception error) {
      error.printStackTrace();
      System.err.println("Interlanl server error in class: " + this.getClass().getName());
      return null;
    }
  }

}
