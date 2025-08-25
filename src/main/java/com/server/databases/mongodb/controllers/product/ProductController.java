package com.server.databases.mongodb.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.server.databases.mongodb.models.product.ProductModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.products.ProductsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mongodb/product")
@CrossOrigin("*")
public class ProductController {

  @Autowired
  private ProductsService productsService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody ProductModel body){
    return productsService.createOne(body);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, ProductModel.class);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(@Valid @ModelAttribute GetManyById body){
    if(body.getId() == null || body.getId().isEmpty()){
      ResponseEntity<Object> response = mainService
      .findAll(ProductModel.class, body.getCollectionName());

      return response;
    } else {
      List<ProductModel> foundProducts = mainService
      .findManyById("id", body.getId(), ProductModel.class, body.getCollectionName());

      return ResponseEntity.ok(foundProducts);
    }
  }

  @GetMapping("/get/recursive")
  public ResponseEntity<Object> findOneByIdRecursive(@Valid @ModelAttribute GetOneById body){
    return productsService.findRecursiveById(body.getId(), body.getCollectionName());
  }

  @GetMapping("/get/recursive/many")
  public ResponseEntity<Object> findManyByIdRecursive(@Valid @ModelAttribute GetManyById body){
    return productsService.findManyRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@Valid @RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ProductModel.class);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@Valid @RequestBody DeleteManyById body){
    return productsService.deleteRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@Valid @RequestParam(required = true) String collectionName){
    return mainService.clearCollection(ProductModel.class, collectionName);
  }
  
}
