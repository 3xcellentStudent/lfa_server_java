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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.DeleteManyFromArray;
import com.server.databases.mongodb.dto.GetManyById;
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

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody MediaModel body){
    return mediaService.createOne(body);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, MediaModel.class);
  }

  @PutMapping("/push")
  public ResponseEntity<Object> pushNewOneToArrayById(@RequestBody UpdateOneByIdDto body){
    return mainService.pushNewOneToArrayById(body, MediaModel.class, body.getCollectionName());
  }

  @DeleteMapping("/delete-many-from-array")
  public ResponseEntity<Object> deleteManyFromArray(@RequestBody DeleteManyFromArray body){
    return mainService.deleteManyFromArrayById(body, MediaModel.class, body.getCollectionName());
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestBody GetManyById body){
    if(body.getId() == null || body.getId().isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(MediaModel.class, body.getCollectionName());

      return response;
    } else {
      List<MediaModel> foundMedia = mainService.findManyById("id", body.getId(), MediaModel.class, body.getCollectionName());

      return ResponseEntity.ok(foundMedia);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(
    @RequestBody DeleteManyById body
  ){
    return mainService.deleteManyById(body, MediaModel.class);
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@RequestParam(required = true) String collectionName){
    DeleteResult result = mongoTemplate.remove(new Query(), MediaModel.class, collectionName);

    return ResponseEntity.ok(result);
  }
  
}
