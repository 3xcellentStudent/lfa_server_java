package com.server.databases.mongodb.services.reviews;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;
import com.server.databases.mongodb.models.product.ProductParentModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;

@Service
public class ReviewsService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MongoDbMainService mainService;

  // public ResponseEntity<Object> createOne(String id, String parent_id, String collectionName, long timestamp){
  //   ReviewsModel newReviewsModel = new ReviewsModel();

  //   newReviewsModel.setId(id);
  //   newReviewsModel.setParentId(parent_id);
  //   newReviewsModel.setCreatedAt();
  //   newReviewsModel.setUpdatedAt();
  //   newReviewsModel.setCollectionName(collectionName);

  //   ReviewsModel response = mongoTemplate.save(newReviewsModel, collectionName);

  //   return ResponseEntity.ok(response);
  // }

  // public ResponseEntity<Object> createOne(String requestBodyString, String collectionName){
  public ResponseEntity<Object> createOne(ReviewsModel body){
    String id = UUID.randomUUID().toString();

    String parentId = body.getParentId();
    updateReviewsArrayByParentId(parentId, id, body.getCollectionName());
    increaseStockInfoFields(parentId, body.getRating(), body.getCollectionName());

    body.setId(id);
    body.setCreatedAt();
    body.setUpdatedAt();

    ReviewsModel savedReview = mongoTemplate.save(body, body.getCollectionName());

    return ResponseEntity.ok(savedReview);
  }

  public ResponseEntity<Object> updateReviewsArrayByParentId(String parentId, String id, String collectionName){
    UpdateOneByIdDto updateReviewsById = new UpdateOneByIdDto(parentId, "reviewsId", id, collectionName);
    return mainService.pushNewOneToArrayById(updateReviewsById, ProductParentModel.class, collectionName);
  }

  public ResponseEntity<Object> increaseStockInfoFields(String parentId, int rating, String collectionName){
    // int countOfReviews = mongoTemplate.findById(parentId, ProductsModel.class).getStockInfo().countOfReviews;
    // int countOfReviews = mongoTemplate.findById(parentId, ProductParentModel.class).getReviewsSnapshot().getTotal();
    // UpdateOneByIdDto updateCountOfReviewsById = new UpdateOneByIdDto(parentId, "stockInfo.countOfReviews", countOfReviews + 1, collectionName);
    // mainService.updateNewOneById(updateCountOfReviewsById, ProductParentModel.class);

    String[] reviewsSnapshotKeys = new String[] {"one", "two", "three", "four", "five"};

    Query query = Query.query(Criteria.where("id").is(parentId));

    ProductParentModel productObject = mongoTemplate.findOne(query, ProductParentModel.class);
    int oneStarCounts = productObject.getReviewsSnapshotByFieldName(reviewsSnapshotKeys[rating - 1]);

    
    UpdateOneByIdDto updateReviewsSnapshotById = new UpdateOneByIdDto(parentId, "stockInfo.reviewsSnapshot." + reviewsSnapshotKeys[rating - 1], oneStarCounts + 1, collectionName);
    return mainService.updateOneById(updateReviewsSnapshotById, ProductParentModel.class);
  }

}
