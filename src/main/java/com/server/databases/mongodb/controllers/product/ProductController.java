package com.server.databases.mongodb.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.main.UpdateOneByIdDto;
import com.server.databases.mongodb.dto.product.CreateNewProductDto;
import com.server.databases.mongodb.models.product.ProductParentModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/mongodb/product")
@CrossOrigin("*")
@Validated
public class ProductController {

  @Value("${databases.mongodb.collections.product.main}")
  private String collection;

  @Autowired
  private ProductService productService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateNewProductDto body){
    return productService.createOne(body);
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    return mainService.updateOneById(body, ProductParentModel.class, collection);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(@RequestParam(required = false)  List<String> ids){
    if(ids == null || ids.isEmpty()){
      ResponseEntity<Object> response = mainService
      .findAll(ProductParentModel.class, collection);

      return response;
    } else {
      List<ProductParentModel> foundProducts = mainService
      .findManyById("id", ids, ProductParentModel.class, collection);

      return ResponseEntity.ok().body(foundProducts);
    }
  }

  @GetMapping("/get/recursive")
  public ResponseEntity<Object> findOneByIdRecursive(@RequestParam @NotBlank String id){
    return productService.findRecursiveById(id, collection);
  }

  @GetMapping("/get/recursive/many")
  public ResponseEntity<Object> findManyByIdRecursive(@RequestParam @NotEmpty List<String> id){
    return productService.findManyRecursiveById(id, collection);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestBody @NotEmpty List<String> ids){
    return mainService.deleteManyById(ids, ProductParentModel.class, collection);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestBody @NotEmpty List<String> ids){
    return productService.deleteRecursiveById(ids, collection);
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return mainService.clearCollection(ProductParentModel.class, collection);
  }
  
}
