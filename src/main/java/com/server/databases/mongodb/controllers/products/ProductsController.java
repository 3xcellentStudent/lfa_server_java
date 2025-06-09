package com.server.databases.mongodb.controllers.products;

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

  @PostMapping("/create")
  public ResponseEntity<Object> create(
    @RequestBody ProductsModel productObject, @RequestParam(name = "collectionName", required = true) String collectionName
  ){
    return productsService.createOne(productObject, collectionName);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body){
    return mainService
    .updateNewOneById(body, ProductsModel.class);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(
    @RequestParam(name = "id", required = false) List<String> id, @RequestParam(name = "collectionName", required = true) String collectionName
  ){
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService
      .findAll(ProductsModel.class, collectionName);

      return response;
    } else {
      List<ProductsModel> foundProducts = mainService.findAllById("id", id, ProductsModel.class, collectionName);

      ResponseEntity<Object> response = mainService.getAsResponseEntity(foundProducts);

      return response;
    }
  }

  @GetMapping("/get/recursive")
  public ResponseEntity<Object> findAllByIdRecursive(@RequestParam List<String> id, String collectionName){
    return productsService.findAllRecursiveById(id, collectionName);
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id, String collectionName){
    return mainService.deleteAllById(id, ProductsModel.class, collectionName);
  }

  @GetMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestParam List<String> id){
    return productsService.deleteRecursiveById(id);
  }

  @GetMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@RequestParam(name = "collectionName", required = true) String collectionName){
    return mainService.clearCollection(ProductsModel.class, collectionName);
  }
  
}
