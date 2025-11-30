package com.server.databases.mongodb.controllers.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.server.databases.mongodb.dto.product.CreateNewProduct;
import com.server.databases.mongodb.models.product.ProductModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/mongodb/product")
@CrossOrigin("*")
@Validated
public class ProductController {

  @Autowired
  private ProductService productService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateNewProduct body){
    return productService.createOne(body);
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
    return productService.findRecursiveById(body.getId(), body.getCollectionName());
  }

  @GetMapping("/get/recursive/many")
  public ResponseEntity<Object> findManyByIdRecursive(@Valid @ModelAttribute GetManyById body){
    return productService.findManyRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@Valid @RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ProductModel.class);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@Valid @RequestBody DeleteManyById body){
    return productService.deleteRecursiveById(body.getId(), body.getCollectionName());
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(
    @RequestParam(required = true) @NotBlank @Pattern(regexp = ".*-.*", message = "collectionName must contain \"-\"") String collectionName
  ){
    return mainService.clearCollection(ProductModel.class, collectionName);
  }
  
}
