package com.server.databases.mongodb.controllers.reviews.diffusers;

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

import com.server.databases.mongodb.helpers.bodies.DeleteManyById;
import com.server.databases.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.databases.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.databases.mongodb.services.MainService;
import com.server.databases.mongodb.services.reviews.diffusers.DiffusersReviewsService;

@RestController
@RequestMapping("/api/mongodb/reviews/diffusers")
@CrossOrigin("*")
public class DiffusersReviewsController {
  
  @Autowired
  private DiffusersReviewsService reviewsService;
  @Autowired
  private MainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = reviewsService.createOne(requestBodyString);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mainService.updateNewOneById(requestBodyString, DiffusersReviewsModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error !");
    }
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    try {
      if(id == null || id.isEmpty()){
        ResponseEntity<Object> response = mainService.findAll(DiffusersReviewsModel.class);

        return response;
      } else {
        List<DiffusersReviewsModel> foundReviews = mainService.findAllById("id", id, DiffusersReviewsModel.class);

        ResponseEntity<Object> response = mainService.getAsResponseEntity(foundReviews);
  
        return response;
      }
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    try {
      ResponseEntity<Object> response = mainService.deleteAllById(id, DiffusersReviewsModel.class);
      
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllById(@RequestBody DeleteManyById requestBody){
    try {
      List<String> id = requestBody.getId();
      String parentId = requestBody.getParentId();

      CompletableFuture<ResponseEntity<Object>> completableFuture = CompletableFuture.supplyAsync(() -> {
        try {
          ResponseEntity<Object> response = mainService.deleteAllById(id, DiffusersReviewsModel.class);

          Update update = new Update().pullAll("reviewsId", id.toArray(new String[0]));
          mongoTemplate.updateMulti(new Query(Criteria.where("id").is(parentId)), update, DiffusersProductsModel.class);
    
          return response;
        } catch(Exception error){
          error.printStackTrace();
          return ResponseEntity.internalServerError().body("Internal serverv error in controller");
        }
      });

      return completableFuture.get();
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    try {
      mongoTemplate.remove(new Query(), DiffusersReviewsModel.class);

      return ResponseEntity.ok("All products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }

}
