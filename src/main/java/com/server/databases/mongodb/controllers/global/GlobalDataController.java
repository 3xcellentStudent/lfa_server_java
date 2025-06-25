package com.server.databases.mongodb.controllers.global;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.result.DeleteResult;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.global.GlobalDataModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.global.GlobalDataService;

@RestController
@RequestMapping("/api/mongodb/global-data")
@CrossOrigin("*")
public class GlobalDataController {

  @Autowired
  private GlobalDataService globalDataService;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;
  
  @Value("${mongodb.collections.global_data}")
  private String collectionName;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    return globalDataService.createOne(requestBodyString);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, GlobalDataModel.class, collectionName);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Object> findById(@PathVariable String id){
    GlobalDataModel foundObject = mongoTemplate.findById(id, GlobalDataModel.class);

    return ResponseEntity.ok(foundObject);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(){
    return mainService.findAll(GlobalDataModel.class, collectionName);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(
    @RequestParam(name = "id", required = true) List<String> id
    ){
    return mainService.deleteManyById(id, GlobalDataModel.class, collectionName);
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    DeleteResult result = mongoTemplate.remove(new Query(), GlobalDataModel.class, collectionName);

    return ResponseEntity.ok(result);
  }
  
}