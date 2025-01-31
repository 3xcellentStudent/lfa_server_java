package com.server.services.mongodb.controllers.global;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.models.global.GlobalDataModel;
import com.server.services.mongodb.repositories.global.GlobalDataRepository;

@RestController
@RequestMapping("/api/mongodb/global-data")
@CrossOrigin("*")
public class GlobalDataController {

  private GlobalDataRepository repository;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody GlobalDataModel GlobalDataModel){
    try {
      if(repository == null || repository.findAll().isEmpty()){
        UUID uuid = UUID.randomUUID();
        GlobalDataModel.setId(uuid.toString());
        GlobalDataModel savedData = repository.save(GlobalDataModel);
        return ResponseEntity.ok().body(savedData);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Error while creating new data to database !");
    }
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateById(@RequestBody GlobalDataModel GlobalDataModel, String id){
    try {
      if(repository != null && repository.findAll().isEmpty() == false && repository.existsById(id)){
        GlobalDataModel savedData = repository.save(GlobalDataModel);
        return ResponseEntity.ok().body(savedData);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Error while updating data in database !");
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> get(){
    try {
      GlobalDataModel GlobalDataModel = repository.findAll().get(0);
      return ResponseEntity.ok().body(GlobalDataModel);
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Error while updating data in database !");
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAll(@RequestBody String id){
    try {
      GlobalDataModel data = repository.findById(id).get();
      return ResponseEntity.ok().body(data);
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Error while updating data in database !");
    }
  }
}
