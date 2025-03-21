package com.server.databases.mongodb.controllers.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.models.global.GlobalDataModel;
import com.server.databases.mongodb.services.MainService;
import com.server.databases.mongodb.services.global.GlobalDataService;

@RestController
@RequestMapping("/api/mongodb/global-data")
@CrossOrigin("*")
public class GlobalDataController {

  @Autowired
  private GlobalDataService globalDataService;
  @Autowired
  private MainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = globalDataService.createOne(requestBodyString);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mainService.updateNewOneById(requestBodyString, GlobalDataModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Object> findById(@PathVariable String id){
    try {
      GlobalDataModel foundObject = mongoTemplate.findById(id, GlobalDataModel.class);

      return ResponseEntity.ok(foundObject);
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(){
    try {
      ResponseEntity<Object> response = mainService.findAll(GlobalDataModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    try {
      ResponseEntity<Object> response =  mainService.deleteAllById(id, GlobalDataModel.class);
      
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    try {
      mongoTemplate.remove(new Query(), GlobalDataModel.class);

      return ResponseEntity.ok("All products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }
  
}