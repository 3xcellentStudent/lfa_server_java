package com.server.databases.mongodb.services.reviews;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;

@Service
public class ReviewsService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MongoDbMainService mainService;

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

  public ResponseEntity<Object> createOne(String requestBodyString, String collectionName){
    String id = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    try {
      DiffusersReviewsModel requestBodyObject = objectMapper.readValue(requestBodyString, DiffusersReviewsModel.class);

      String parentId = requestBodyObject.getParentId();
      updateReviewsArrayByParentId(parentId, id, collectionName);
      increaseStockInfoFields(parentId, requestBodyObject.rating, collectionName);

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

  public ResponseEntity<Object> updateReviewsArrayByParentId(String parentId, String id, String collectionName){
    UpdateOneByIdDto updateReviewsById = new UpdateOneByIdDto(parentId, "reviewsId", id, collectionName);
    return mainService.pushNewOneToArrayById(updateReviewsById, ProductsModel.class);
  }

  public ResponseEntity<Object> increaseStockInfoFields(String parentId, int rating, String collectionName){
    int countOfReviews = mongoTemplate.findById(parentId, ProductsModel.class).getStockInfo().countOfReviews;
    UpdateOneByIdDto updateCountOfReviewsById = new UpdateOneByIdDto(parentId, "stockInfo.countOfReviews", countOfReviews + 1, collectionName);
    mainService.updateNewOneById(updateCountOfReviewsById, ProductsModel.class);

    String[] reviewsSnapshotKeys = new String[] {"one", "two", "three", "four", "five"};

    Query query = Query.query(Criteria.where("id").is(parentId));

    ProductsModel productObject = mongoTemplate.findOne(query, ProductsModel.class);
    int oneStarCounts = productObject.getReviewsSnapshotByFieldName(reviewsSnapshotKeys[rating - 1]);
    UpdateOneByIdDto updateReviewsSnapshotById = new UpdateOneByIdDto(parentId, "stockInfo.reviewsSnapshot." + reviewsSnapshotKeys[rating - 1], oneStarCounts + 1, collectionName);
    return mainService.updateNewOneById(updateReviewsSnapshotById, ProductsModel.class);
  }

}
