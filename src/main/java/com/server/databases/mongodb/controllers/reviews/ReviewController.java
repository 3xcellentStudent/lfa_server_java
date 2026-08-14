package com.server.databases.mongodb.controllers.reviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
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

import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;
import com.server.databases.mongodb.models.product.parent.ProductParentModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.reviews.ReviewsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/mongodb/review")
@CrossOrigin("*")
@Validated
public class ReviewController {

  @Value("${databases.mongodb.collections.reviews}")
  private String collection;
    @Value("${databases.mongodb.collections.products.main}")
  private String productCollection;

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

  @PatchMapping("/update/id")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    ReviewsModel mofidiedDoc = mainService.updateOneById(body, ReviewsModel.class, collection);

    if(mofidiedDoc == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document with ID \" " + body.id() + " \" was not found !");
    }
    return ResponseEntity.ok(mofidiedDoc);
  }
  
  @GetMapping("/get/id")
  public ResponseEntity<Object> findManyById(@RequestParam @NotEmpty List<String> ids){
    List<ReviewsModel> docsList = mainService.findManyById("_id", ids, ReviewsModel.class, collection);
    return ResponseEntity.ok(docsList);
  }

  @GetMapping("/get/parent-id")
  public ResponseEntity<Object> findManyByParentId(@RequestParam @NotBlank String parentId){
    Query query = Query.query(Criteria.where("_id").is(parentId));
    boolean isExists = mongoTemplate.exists(query, ProductParentModel.class, productCollection);
    if(!isExists){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document with ID \"" + parentId + "\" does not exist !");
    }
    List<ReviewsModel> docslist =  mainService.findManyById("parentId", parentId, ReviewsModel.class, collection);

    return ResponseEntity.ok(docslist);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteManyById(@RequestBody @NotEmpty List<String> ids){
    List<ReviewsModel> removedDocList = mainService.deleteManyById(ids, ReviewsModel.class, collection);
    // if(docsList.isEmpty()){
    //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(docsList);
    // }

    return ResponseEntity.ok(removedDocList);
  }

  @DeleteMapping("/delete/id")
  public ResponseEntity<Object> deleteOneById(@RequestBody @NotBlank String id){
    Query query = Query.query(Criteria.where("_id").is(id));
    ReviewsModel removedDoc = mongoTemplate.findAndRemove(query, ReviewsModel.class, collection);

    return ResponseEntity.ok(removedDoc);
  }

  @DeleteMapping("/delete/parent-id")
  public ResponseEntity<Object> deletemanyByParentId(@RequestBody @NotBlank String parentId){
    Query query = Query.query(Criteria.where("parentId").is(parentId));
    List<ReviewsModel> removedDocList = mongoTemplate.findAllAndRemove(query, ReviewsModel.class, collection);

    return ResponseEntity.ok(removedDocList);
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    Long deletedCount = mainService.clearCollection(ReviewsModel.class, collection);

    return ResponseEntity.ok(deletedCount);
  }

}
