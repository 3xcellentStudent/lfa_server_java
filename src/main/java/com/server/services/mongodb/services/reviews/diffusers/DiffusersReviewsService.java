package com.server.services.mongodb.services.reviews.diffusers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.helpers.queries.QueriesHelper;
import com.server.services.mongodb.models.reviews.ReviewsModel.Review;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.services.mongodb.repositories.reviews.diffusers.DiffusersReviewsRepository;

@Service
public class DiffusersReviewsService {
  
  @Autowired
  private DiffusersReviewsRepository reviewsRepository;
  @Autowired
  private DiffusersReviewsModel reviewsModel;
  @Autowired
  private MongoTemplate mongoTemplate;

  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createOne(String id, String parent_id, String media_id, long timestamp){
    try {
      reviewsModel.setParentId(parent_id);
      reviewsModel.setMediaId(media_id);
      reviewsModel.setId(id);
      reviewsModel.setCreateTime(timestamp);
      reviewsModel.setUpdateTime(timestamp);
      reviewsModel.reviewsList = new ArrayList<>();

      DiffusersReviewsModel savedReview = reviewsRepository.save(reviewsModel);

      return ResponseEntity.ok(savedReview);
    } catch(IllegalArgumentException error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> createOne(DiffusersReviewsModel requestBody){
    String id = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();
    
    try {
      requestBody.setId(id);
      requestBody.setCreateTime(timestamp);
      requestBody.setUpdateTime(timestamp);

      DiffusersReviewsModel savedReview = reviewsRepository.save(requestBody);

      return ResponseEntity.ok(savedReview);
    } catch(IllegalArgumentException error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> updateOneById(Iterable<String> id, Iterable<DiffusersReviewsModel> reviews){
    try {
      long timestamp = System.currentTimeMillis();

      List<DiffusersReviewsModel> updatedReviews = reviewsRepository.findAllById(id).stream()
      .map(review -> {
        review.setUpdateTime(timestamp);
        return review;
      }).filter(Objects::nonNull).toList();

      List<DiffusersReviewsModel> savedReviews = reviewsRepository.saveAll(updatedReviews);

      return ResponseEntity.ok(savedReviews);
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error: " + error.getMessage());
    }
  }

  public ResponseEntity<Object> findAllById(List<String> id){
    try {
      List<DiffusersReviewsModel> foundReviews = mongoTemplate.find(QueriesHelper.getId("id", id), DiffusersReviewsModel.class);

      String response = objectMapper.writeValueAsString(foundReviews);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> findOneAndSliceReviewsList(String id, int fromIndex, int toIndex){
    try {
      DiffusersReviewsModel slicedReviews = mongoTemplate
      .findById(QueriesHelper.getSliceOfReviewsList("reviewsList", id, fromIndex, toIndex), DiffusersReviewsModel.class);

      // List<DiffusersReviewsModel> modifiedObjects = foundReviews.stream()
      // .map(object -> {
      //   int newToIndex = toIndex < collectionSize ? toIndex : collectionSize - 1;
      //   List<Review> tenReviews = object.reviewsList.subList(fromIndex, newToIndex);
      //   object.reviewsList = tenReviews;
        
      //   return object;
      // }).filter(Objects::nonNull).toList();

      String response = objectMapper.writeValueAsString(slicedReviews);

      return ResponseEntity.ok(response);
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> deleteById(Iterable<String> id){
    try {
      List<DiffusersReviewsModel> foundReviews = reviewsRepository.findAllById(id)
      .stream().filter(Objects::nonNull).toList();

      if(foundReviews.isEmpty()){
        return ResponseEntity.status(404).body("Don't find documents with IDs: " + id);
      } else {
        List<String> reviews_id = foundReviews.stream()
        .map(DiffusersReviewsModel::getId).filter(Objects::nonNull).toList();
  
        reviewsRepository.deleteAllById(reviews_id);
  
        return ResponseEntity.ok(foundReviews);
      }
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

}
