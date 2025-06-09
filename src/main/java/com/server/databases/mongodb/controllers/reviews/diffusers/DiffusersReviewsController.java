package com.server.databases.mongodb.controllers.reviews.diffusers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.reviews.ReviewsService;

@RestController
@RequestMapping("/api/mongodb/reviews/diffusers")
@CrossOrigin("*")
public class DiffusersReviewsController {
  
  @Autowired
  private ReviewsService reviewsService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Value("${mongodb.collections.reviews.diffusers}")
  private String collectionName;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    return reviewsService.createOne(requestBodyString,collectionName);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    return mainService.updateNewOneById(requestBodyString, DiffusersReviewsModel.class, collectionName);
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(DiffusersReviewsModel.class, collectionName);

      return response;
    } else {
      List<DiffusersReviewsModel> foundReviews = mainService.findAllById("id", id, DiffusersReviewsModel.class, collectionName);

      ResponseEntity<Object> response = mainService.getAsResponseEntity(foundReviews);

      return response;
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    return mainService.deleteAllById(id, DiffusersReviewsModel.class, collectionName);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllById(@RequestBody DeleteManyById requestBody){
    List<String> id = requestBody.getId();
    String parentId = requestBody.getParentId();

    CompletableFuture<ResponseEntity<Object>> completableFuture = CompletableFuture.supplyAsync(() -> {
      ResponseEntity<Object> response = mainService.deleteAllById(id, DiffusersReviewsModel.class, collectionName);

      Update update = new Update().pullAll("reviewsId", id.toArray(new String[0]));
      mongoTemplate.updateMulti(new Query(Criteria.where("id").is(parentId)), update, ProductsModel.class);

      return response;
    });

    return completableFuture.join();
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    mongoTemplate.remove(new Query(), DiffusersReviewsModel.class);

    return ResponseEntity.ok("All products have been removed from database !");
  }

}
