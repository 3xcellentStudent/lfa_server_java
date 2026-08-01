package com.server.databases.mongodb.controllers.product.variation;

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
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.variation.ProductVariationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/mongodb/product/variation")
@CrossOrigin("*")
@Validated
public class ProductVariationController {
  
  @Value("${databases.mongodb.collections.product.variation}")
  private String collection;

  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private ProductVariationService productVariationService;

  @PostMapping("/create/parent-id")
  public ResponseEntity<Object> createByParentId(@Valid @RequestBody CreateVariationByParentId body){
    return productVariationService.createByParentId(body);
  }

  @GetMapping("/get/parent-id")
  public ResponseEntity<Object> getByParentId(
    @RequestParam @NotBlank String id
    // @RequestParam(required = true) @NotBlank @Pattern(regexp = ".*_.*", message = "collectionName must contain \"_\"") String collectionName
  ){
    List<ProductVariationModel> productVariation = mainService
    .findManyById("parentId", id, ProductVariationModel.class, collection);

    return ResponseEntity.ok(productVariation);
  }

  @GetMapping("/get/id")
  public ResponseEntity<Object> getManyById(@RequestParam @NotEmpty List<String> id){
    List<ProductVariationModel> productVariation = mainService
    .findManyById("_id", id, ProductVariationModel.class, collection);

    return ResponseEntity.ok(productVariation);
  }

  @PatchMapping("/update/id")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    return mainService.updateOneById(body, ProductVariationModel.class, collection);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteById(@RequestBody @NotEmpty List<String> ids){
    return productVariationService.deteleManyById(ids);
  }
  
  @DeleteMapping("/clear")
  public ResponseEntity<Object> clearCollection(
    // @RequestParam(required = true) @NotBlank @Pattern(regexp = ".*-.*", message = "collectionName must contain \"-\"") String collectionName
  ){
    return mainService.clearCollection(ProductVariationModel.class, collection);
  }

}
