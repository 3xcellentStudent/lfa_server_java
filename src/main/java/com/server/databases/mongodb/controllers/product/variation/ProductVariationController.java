package com.server.databases.mongodb.controllers.product.variation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.GetManyById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.products.variations.ProductVariations;
import com.server.databases.mongodb.services.MongoDbMainService;

@RestController
@RequestMapping("/api/mongodb/product/variation")
public class ProductVariationController {
  
  @Autowired
  private MongoDbMainService mainService;

  @GetMapping("/get/parent-id")
  public ResponseEntity<Object> getByParentId(@RequestParam String id, @RequestParam String collectionName){
    List<ProductVariations> productVariations = mainService
    .findManyById("parentId", id, ProductVariations.class, collectionName);

    return ResponseEntity.ok(productVariations);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getManyById(@RequestBody GetManyById body){
    List<ProductVariations> productVariations = mainService
    .findManyById("id", body.getId(), ProductVariations.class, body.getCollectionName());

    return ResponseEntity.ok(productVariations);
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody UpdateOneByIdDto body){
    return mainService.updateNewOneById(body, ProductVariations.class);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteById(@RequestBody DeleteManyById body){
    return mainService.deleteManyById(body, ProductVariations.class);
  }
  
  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(@PathVariable(required = true) String collectionName){
    return mainService.clearCollection(ProductVariations.class, collectionName);
  }

}
