package com.server.databases.mongodb.controllers.reviews;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.server.databases.mongodb.dto.main.DeleteManyById;
import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;
import com.server.databases.mongodb.models.product.ProductParentModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.reviews.ReviewsService;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/mongodb/review")
@CrossOrigin("*")
@Validated
public class ReviewController {

  @Value("${databases.mongodb.collections.reviews}")
  private String collection;

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
    return mainService.updateOneById(body, ReviewsModel.class, collection);
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findManyById(@RequestParam(required = false) @Nullable List<String> id){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(ReviewsModel.class, collection);

      return response;
    } else {
      List<ReviewsModel> foundReviews = mainService.findManyById("id", id, ReviewsModel.class, collection);

      return ResponseEntity.ok(foundReviews);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteManyById(@RequestBody @NotEmpty List<String> ids){
    return mainService.deleteManyById(ids, ReviewsModel.class, collection);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteManyByIdRecursive(@Valid @RequestBody DeleteManyById body){
    CompletableFuture<ResponseEntity<Object>> completableFuture = CompletableFuture.supplyAsync(() -> {
      ResponseEntity<Object> response = mainService.deleteManyById(body.ids(), ReviewsModel.class, collection);

      Update update = new Update().pullAll("reviewsId", body.ids().toArray(new String[0]));
      mongoTemplate.updateMulti(new Query(Criteria.where("id")
      .is(body.parentId())), update, ProductParentModel.class, collection);

      return response;
    });

    return completableFuture.join();
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection
  (
    @RequestParam(required = true) @NotBlank @Pattern(regexp = ".*-.*", message = "collectionName must contain \"-\"") String collectionName
  ){
    DeleteResult result = mongoTemplate.remove(new Query(), ReviewsModel.class, collectionName);

    return ResponseEntity.ok(result);
  }

}
