package com.server.databases.mongodb.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.GetManyById;
import com.server.databases.mongodb.dto.GetOneById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.products.ProductsModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.products.ProductsService;

@RestController
@RequestMapping("/api/mongodb/product")
@CrossOrigin("*")
public class ProductController {

  @Autowired
  private ProductsService productsService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody ProductsModel body){
    return productsService.createOne(body);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, ProductsModel.class);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(@RequestParam(required = false) List<String> id, @RequestParam String collectionName){
    System.out.println(id);
    System.out.println(id.isEmpty());
    if(id == null || id.isEmpty()){
      ResponseEntity<Object> response = mainService
      .findAll(ProductsModel.class, collectionName);

      return response;
    } else {
      List<ProductsModel> foundProducts = mainService
      .findManyById("id", id, ProductsModel.class, collectionName);

      return ResponseEntity.ok(foundProducts);
    }
  }

  @PostMapping("/get/recursive")
  public ResponseEntity<Object> findOneByIdRecursive(@RequestBody GetOneById body){
    return productsService.findRecursiveById(body.getId(), body.getCollectionName());
  }

  @PostMapping("/get/recursive/many")
  public ResponseEntity<Object> findManyByIdRecursive(@RequestBody GetManyById body){
    return productsService.findManyRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ProductsModel.class);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestBody GetManyById body){
    return productsService.deleteRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@RequestParam(required = true) String collectionName){
    return mainService.clearCollection(ProductsModel.class, collectionName);
  }
  
}
