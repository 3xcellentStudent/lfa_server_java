package com.server.databases.mongodb.controllers.reviews;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.reviews.ReviewsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/mongodb/review")
@CrossOrigin("*")
public class ReviewController {
  
  @Autowired
  private ReviewsService reviewsService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody ReviewsModel body){
    return reviewsService.createOne(body);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, ReviewsModel.class);
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findManyById(
    @RequestParam(required = false) List<String> id, @NotBlank @RequestParam(required = true) String collectionName
  ){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(ReviewsModel.class, collectionName);

      return response;
    } else {
      List<ReviewsModel> foundReviews = mainService.findManyById("id", id, ReviewsModel.class, collectionName);

      return ResponseEntity.ok(foundReviews);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteManyById(@Valid @RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ReviewsModel.class);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteManyByIdRecursive(@Valid @RequestBody DeleteManyById body){
    CompletableFuture<ResponseEntity<Object>> completableFuture = CompletableFuture.supplyAsync(() -> {
      ResponseEntity<Object> response = mainService.deleteManyById(body, ReviewsModel.class);

      Update update = new Update().pullAll("reviewsId", body.getId().toArray(new String[0]));
      mongoTemplate.updateMulti(new Query(Criteria.where("id")
      .is(body.getParentId())), update, ProductsModel.class, body.getCollectionName());

      return response;
    });

    return completableFuture.join();
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@Valid @RequestParam(required = true) String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), ReviewsModel.class, collectionName);

    return ResponseEntity.ok(result);
  }

}
