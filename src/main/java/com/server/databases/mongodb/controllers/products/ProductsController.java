package com.server.databases.mongodb.controllers.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.products.ProductsService;

@RestController
@RequestMapping("/api/mongodb/products")
@CrossOrigin("*")
public class ProductsController {

  @Autowired
  private ProductsService productsService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create/{collectionName}")
  public ResponseEntity<Object> create(@RequestBody ProductsModel body, @PathVariable(required = true) String collectionName){
    return productsService.createOne(body, collectionName);
  }

  @PatchMapping("/update/{collectionName}")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body, @PathVariable(required = true) String collectionName){
    return mainService.updateNewOneById(body, ProductsModel.class, collectionName);
  }

  @GetMapping("/get/{collectionName}")
  public ResponseEntity<Object> findAll(
    @RequestParam(name = "id", required = false) List<String> id, @PathVariable(required = true) String collectionName
  ){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService
      .findAll(ProductsModel.class, collectionName);

      return response;
    } else {
      List<ProductsModel> foundProducts = mainService.findAllById("id", id, ProductsModel.class, collectionName);

      return ResponseEntity.ok(foundProducts);
    }
  }

  @GetMapping("/get/recursive/{collectionName}")
  public ResponseEntity<Object> findAllByIdRecursive(
    @RequestParam List<String> id, @PathVariable(required = true) String collectionName
  ){
    return productsService.findAllRecursiveById(id, collectionName);
  }

  @DeleteMapping("/delete/{collectionName}")
  public ResponseEntity<Object> deleteAllById(
    @RequestParam(name = "id", required = true) List<String> id, @PathVariable(required = true) String collectionName
  ){
    return mainService.deleteManyById(id, ProductsModel.class, collectionName);
  }

  @DeleteMapping("/delete/recursive/{collectionName}")
  public ResponseEntity<Object> deleteAllByIdRecursive(
    @RequestParam(name = "id", required = true) List<String> id, @PathVariable(required = true) String collectionName
  ){
    return productsService.deleteRecursiveById(id, collectionName);
  }

  @DeleteMapping("/clear-col/{collectionName}")
  public ResponseEntity<Object> clearCollection(@PathVariable(required = true) String collectionName){
    return mainService.clearCollection(ProductsModel.class, collectionName);
  }
  
}
