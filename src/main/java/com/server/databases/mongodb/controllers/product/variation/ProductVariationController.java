package com.server.databases.mongodb.controllers.product.variation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.GetManyById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.products.variation.ProductVariationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mongodb/product/variation")
@CrossOrigin("*")
public class ProductVariationController {
  
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private ProductVariationService productVariationService;

  @PostMapping("/create/parent-id")
  public ResponseEntity<Object> createByParentId(@Valid @RequestBody ProductVariationModel body){
    return productVariationService.createByParentId(body);
  }

  @GetMapping("/get/parent-id")
  public ResponseEntity<Object> getByParentId(@Valid @RequestParam String id, @RequestParam String collectionName){
    List<ProductVariationModel> productVariation = mainService
    .findManyById("parentId", id, ProductVariationModel.class, collectionName);

    return ResponseEntity.ok(productVariation);
  }

  @GetMapping("/get/by-id")
  public ResponseEntity<Object> getManyById(@Valid @ModelAttribute GetManyById body){
    List<ProductVariationModel> productVariation = mainService
    .findManyById("id", body.getId(), ProductVariationModel.class, body.getCollectionName());

    return ResponseEntity.ok(productVariation);
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, ProductVariationModel.class);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteById(@Valid @RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ProductVariationModel.class);
  }
  
  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@Valid @RequestParam(required = true) String collectionName){
    return mainService.clearCollection(ProductVariationModel.class, collectionName);
  }

}
