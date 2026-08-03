package com.server.databases.mongodb.controllers.reviews;

import java.util.HashMap;
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

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;
import com.server.databases.mongodb.models.reviews.ReviewsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.reviews.ReviewsService;

import jakarta.annotation.Nullable;
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
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mofidiedDoc);
    }
    return ResponseEntity.ok(mofidiedDoc);
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findManyById(@RequestParam(required = false) @Nullable List<String> id){
    List<ReviewsModel> docsList = id == null || id.isEmpty() 
    ? mainService.findAll(ReviewsModel.class, collection) 
    : mainService.findManyById("id", id, ReviewsModel.class, collection);

    if(docsList.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(docsList);
    }
    return ResponseEntity.ok(docsList);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteManyById(@RequestBody @NotEmpty List<String> ids){
    List<ReviewsModel> docsList = mainService.deleteManyById(ids, ReviewsModel.class, collection);

    if(docsList.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(docsList);
    }

    return ResponseEntity.ok(docsList);
  }

  @DeleteMapping("/delete/id")
  public ResponseEntity<Object> deleteOneById(@RequestBody @NotBlank String id){
    Query query = Query.query(Criteria.where("id").is(id));
    DeleteResult result = mongoTemplate.remove(query, collection);

    return ResponseEntity.ok(new HashMap<>().put("count", result.getDeletedCount()));
  }

  @DeleteMapping("/delete/parent-id")
  public ResponseEntity<Object> deletemanyByParentId(@RequestBody @NotEmpty List<String> id){
    Query query = Query.query(Criteria.where("id").in(id));
    DeleteResult result = mongoTemplate.remove(query, collection);

    return ResponseEntity.ok(new HashMap<>().put("count", result.getDeletedCount()));
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    Long result = mainService.clearCollection(ReviewsModel.class, collection);

    return ResponseEntity.ok(result);
  }

}
