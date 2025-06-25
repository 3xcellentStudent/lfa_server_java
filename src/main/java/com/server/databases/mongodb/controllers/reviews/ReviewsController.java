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
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping("/api/mongodb/reviews")
@CrossOrigin("*")
public class ReviewsController {
  
  @Autowired
  private ReviewsService reviewsService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create/{collectionName}")
  public ResponseEntity<Object> create(@RequestBody ReviewsModel body, @PathVariable(required = true) String collectionName){
    return reviewsService.createOne(body, collectionName);
  }

  @PatchMapping("/update/{collectionName}")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body, @PathVariable(required = true) String collectionName){
    return mainService.updateNewOneById(body, ReviewsModel.class, collectionName);
  }
  
  @GetMapping("/get/{collectionName}")
  public ResponseEntity<Object> findAllById(
    @RequestParam(name = "id", required = false) List<String> id, @PathVariable(required = true) String collectionName
  ){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(ReviewsModel.class, collectionName);

      return response;
    } else {
      List<ReviewsModel> foundReviews = mainService.findAllById("id", id, ReviewsModel.class, collectionName);

      return ResponseEntity.ok(foundReviews);
    }
  }

  @DeleteMapping("/delete/{collectionName}")
  public ResponseEntity<Object> deleteAllById(
    @RequestParam(name = "id", required = true) List<String> id, @PathVariable(required = true) String collectionName
  ){
    return mainService.deleteManyById(id, ReviewsModel.class, collectionName);
  }

  @DeleteMapping("/delete/recursive/{collectionName}")
  public ResponseEntity<Object> deleteAllById(@RequestBody DeleteManyById body, @PathVariable(required = true) String collectionName){
    List<String> id = body.getId();
    String parentId = body.getParentId();

    CompletableFuture<ResponseEntity<Object>> completableFuture = CompletableFuture.supplyAsync(() -> {
      ResponseEntity<Object> response = mainService.deleteManyById(id, ReviewsModel.class, collectionName);

      Update update = new Update().pullAll("reviewsId", id.toArray(new String[0]));
      mongoTemplate.updateMulti(new Query(Criteria.where("id")
      .is(parentId)), update, ProductsModel.class, collectionName);

      return response;
    });

    return completableFuture.join();
  }

  @DeleteMapping("/clear-col/{collectionName}")
  public ResponseEntity<Object> clearCollection(@PathVariable(required = true) String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), ReviewsModel.class, collectionName);

    return ResponseEntity.ok(result);
  }

}
