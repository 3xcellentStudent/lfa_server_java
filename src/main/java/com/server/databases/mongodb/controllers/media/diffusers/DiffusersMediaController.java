package com.server.databases.mongodb.controllers.media.diffusers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.media.diffusers.DiffusersMediaService;

@RestController
@RequestMapping("/api/mongodb/media/diffusers")
@CrossOrigin("*")
public class DiffusersMediaController {

  @Autowired
  private DiffusersMediaService mediaService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Value("${mongodb.collections.media}")
  private String collectionName;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    return mediaService.createOne(requestBodyString);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    return mainService.updateNewOneById(requestBodyString, DiffusersMediaModel.class, collectionName);
  }

  @PutMapping("/push")
  public ResponseEntity<Object> pushNewOneToArrayById(@RequestBody String requestBodyString){
    return mainService.pushNewOneToArrayById(requestBodyString, DiffusersMediaModel.class, collectionName);
  }

  @DeleteMapping("/delete-many-from-array")
  public ResponseEntity<Object> deleteManyFromArray(@RequestBody String requestBodyString){
    return mainService.deleteManyFromArrayById(requestBodyString, DiffusersMediaModel.class, collectionName);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(DiffusersMediaModel.class, collectionName);

      return response;
    } else {
      List<DiffusersMediaModel> foundMedia = mainService.findAllById("id", id, DiffusersMediaModel.class, collectionName);

      ResponseEntity<Object> response = mainService.getAsResponseEntity(foundMedia);

      return response;
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    return mainService.deleteAllById(id, DiffusersMediaModel.class, collectionName);
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    mongoTemplate.remove(new Query(), DiffusersMediaModel.class);

    return ResponseEntity.ok("All products have been removed from database !");
  }
  
}
