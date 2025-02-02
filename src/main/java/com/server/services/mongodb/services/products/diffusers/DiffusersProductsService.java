package com.server.services.mongodb.services.products.diffusers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
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
        String reviews_id = CustomUUID.fromString(id);
        String media_id = CustomUUID.fromString(id);

        long timestamp = System.currentTimeMillis();
  
        Object reviewsServiceEntity = reviewsService.createOne(reviews_id, id, media_id, timestamp).getBody();
        Object mediaServiceEntity = mediaService.createOne(media_id, id, reviews_id, timestamp).getBody();
        
        if(reviewsServiceEntity != null && mediaServiceEntity != null){
          try {
            requestBodyObject.setId(id);
            requestBodyObject.setReviewsId(reviews_id);
            requestBodyObject.setMediaId(media_id);
            requestBodyObject.setCreateTime(timestamp);
            requestBodyObject.setUpdateTime(timestamp);

            requestBodyObject.setReviews((DiffusersReviewsModel) reviewsServiceEntity);
            requestBodyObject.setMediaContent((DiffusersMediaModel) mediaServiceEntity);

            DiffusersProductsModel savedProduct = mongoTemplate.save(requestBodyObject);

            String stringResponse = objectMapper.writeValueAsString(savedProduct);

            return ResponseEntity.ok(stringResponse);
          } catch (Exception error) {
            error.printStackTrace();
            System.err.println("Internal server error in class: " + this.getClass().getName() + "\n with error: " + error.getMessage());
            return null;
          }
        } else {
          return ResponseEntity.status(404).body(new ArrayList<>());
        }
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
      .findAllAndRemove(QueriesHelper.getId("parent_id", id), DiffusersReviewsModel.class);
      List<DiffusersMediaModel> removedMedia = mongoTemplate
      .findAllAndRemove(QueriesHelper.getId("parent_id", id), DiffusersMediaModel.class);

      JSONObject jsonBody = new JSONObject()
      .put("products", objectMapper.writeValueAsString(removedProducts))
      .put("reviews", objectMapper.writeValueAsString(removedReviews))
      .put("media", objectMapper.writeValueAsString(removedMedia));

      return ResponseEntity.ok(jsonBody);
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
        Object reviewsModifiedEntity = reviewsService.findOneAndSliceReviewsList(oneObject.getReviewsId(), 0, 9).getBody();
        DiffusersReviewsModel reviewsModifiedObject = objectMapper
        .convertValue(reviewsModifiedEntity, DiffusersReviewsModel.class);

        oneObject.setReviews(reviewsModifiedObject);
        oneObject.setMediaContent(mongoTemplate.findById(oneObject.getMediaId(), DiffusersMediaModel.class));
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
