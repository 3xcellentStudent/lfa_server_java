package com.server.databases.mongodb.controllers.products.diffusers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.products.diffusers.DiffusersProductsService;

@RestController
@RequestMapping("/api/mongodb/products/diffusers")
@CrossOrigin("*")
public class DiffusersProductsController {

  @Autowired
  private DiffusersProductsService productsService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    return productsService.createOne(requestBodyString);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mainService.updateNewOneById(requestBodyString, DiffusersProductsModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }

  @GetMapping("/find")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService.findAll(DiffusersProductsModel.class);

      return response;
    } else {
      List<DiffusersProductsModel> foundProducts = mainService.findAllById("id", id, DiffusersProductsModel.class);

      ResponseEntity<Object> response = mainService.getAsResponseEntity(foundProducts);

      return response;
    }
  }

  @GetMapping("/find/recursive")
  public ResponseEntity<Object> findAllByIdRecursive(@RequestParam List<String> id){
    return productsService.findAllRecursive(id);
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    return mainService.deleteAllById(id, DiffusersProductsModel.class);
  }

  @GetMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestParam List<String> id){
      return productsService.deleteRecursiveById(id);
  }

  @GetMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return mainService.clearCollection(DiffusersProductsModel.class);
  }
  
}
