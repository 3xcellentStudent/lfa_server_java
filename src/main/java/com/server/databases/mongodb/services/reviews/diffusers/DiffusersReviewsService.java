package com.server.databases.mongodb.services.reviews.diffusers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.helpers.bodies.UpdateOneById;
import com.server.databases.mongodb.helpers.queries.QueriesHelper;
import com.server.databases.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.MainService;

@Service
public class DiffusersReviewsService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MainService mainService;

  public ResponseEntity<Object> createOne(String id, String parent_id, long timestamp){
    try {
      DiffusersReviewsModel newObject = new DiffusersReviewsModel();

      newObject.setId(id);
      newObject.setParentId(parent_id);
      newObject.setCreateAt(timestamp);
      newObject.setUpdateAt(timestamp);
      // newObject.reviewsList = new ArrayList<>();

      DiffusersReviewsModel response = mongoTemplate.save(newObject);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> createOne(String requestBodyString){
    String id = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    try {
      DiffusersReviewsModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersReviewsModel.class);

      String parentId = requestBodyObject.getParentId();
      updateReviewsArrayByParentId(parentId, id);
      increaseStockInfoFields(parentId, requestBodyObject.rating);

      requestBodyObject.setId(id);
      requestBodyObject.setCreateAt(timestamp);
      requestBodyObject.setUpdateAt(timestamp);

      DiffusersReviewsModel savedReview = mongoTemplate.save(requestBodyObject);

      String response = objectMapper.writeValueAsString(savedReview);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> updateReviewsArrayByParentId(String parentId, String id){
    UpdateOneById updateReviewsById = new UpdateOneById(parentId, "reviewsId", id);
    return mainService.pushNewOneToArrayById(updateReviewsById, DiffusersProductsModel.class);
  }

  public ResponseEntity<Object> increaseStockInfoFields(String parentId, int rating){
    int countOfReviews = mongoTemplate.findById(parentId, DiffusersProductsModel.class).stockInfo.countOfReviews;
    UpdateOneById updateCountOfReviewsById = new UpdateOneById(parentId, "stockInfo.countOfReviews", countOfReviews + 1);
    mainService.updateNewOneById(updateCountOfReviewsById, DiffusersProductsModel.class);

    String[] reviewsSnapshotKeys = new String[] {"one", "two", "three", "four", "five"};

    DiffusersProductsModel productObject = mongoTemplate.findOne(QueriesHelper.getId("id", parentId), DiffusersProductsModel.class);
    int oneStarCounts = productObject.getReviewsSnapshotByFieldName(reviewsSnapshotKeys[rating - 1]);
    UpdateOneById updateReviewsSnapshotById = new UpdateOneById(parentId, "stockInfo.reviewsSnapshot." + reviewsSnapshotKeys[rating - 1], oneStarCounts + 1);
    return mainService.updateNewOneById(updateReviewsSnapshotById, DiffusersProductsModel.class);
  }

}
