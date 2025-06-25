package com.server.databases.mongodb.controllers.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.DeleteManyFromArray;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.media.MediaModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.media.MediaService;

@RestController
@RequestMapping("/api/mongodb/media")
@CrossOrigin("*")
public class MediaController {

  @Autowired
  private MediaService mediaService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create/{collectionName}")
  public ResponseEntity<Object> create(@RequestBody MediaModel body, @PathVariable(required = true) String collectionName){
    return mediaService.createOne(body, collectionName);
  }

  @PatchMapping("/update/{collectionName}")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body, @PathVariable(required = true) String collectionName){
    return mainService.updateNewOneById(body, MediaModel.class, collectionName);
  }

  @PutMapping("/push/{collectionName}")
  public ResponseEntity<Object> pushNewOneToArrayById(@RequestBody UpdateOneByIdDto body, @PathVariable(required = true) String collectionName){
    return mainService.pushNewOneToArrayById(body, MediaModel.class, collectionName);
  }

  @DeleteMapping("/delete-many-from-array/{collectionName}")
  public ResponseEntity<Object> deleteManyFromArray(@RequestBody DeleteManyFromArray body, @PathVariable(required = true) String collectionName){
    return mainService.deleteManyFromArrayById(body, MediaModel.class, collectionName);
  }

  @GetMapping("/get/{collectionName}")
  public ResponseEntity<Object> findAllById(
    @RequestParam(name = "id", required = false) List<String> id, @PathVariable(required = true) String collectionName
  ){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(MediaModel.class, collectionName);

      return response;
    } else {
      List<MediaModel> foundMedia = mainService.findAllById("id", id, MediaModel.class, collectionName);

      return ResponseEntity.ok(foundMedia);
    }
  }

  @DeleteMapping("/delete/{collectionName}")
  public ResponseEntity<Object> deleteAllById(
    @RequestParam(name = "id", required = true) List<String> id, @PathVariable(required = true) String collectionName
  ){
    return mainService.deleteManyById(id, MediaModel.class, collectionName);
  }

  @DeleteMapping("/clear-col/{collectionName}")
  public ResponseEntity<Object> clearCollection(@PathVariable(required = true) String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), MediaModel.class, collectionName);

    return ResponseEntity.ok(result);
  }
  
}
