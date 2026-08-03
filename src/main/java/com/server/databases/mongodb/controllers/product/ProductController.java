package com.server.databases.mongodb.controllers.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
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
  @Value("${databases.mongodb.collections.product.variation}")
  private String variationCollection;

  private final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;
  @Autowired
  private MongoDbMainService mainService;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateNewProductDto body){
    return ResponseEntity.ok(productService.createOne(body));
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    ProductParentModel modifiedDoc = mainService.updateOneById(body, ProductParentModel.class, collection);

    if(modifiedDoc == null){
      String message = "Document with ID: \"" + body.id() + "\" was not found";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    } else {
      return ResponseEntity.ok(modifiedDoc);
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAll(@RequestParam(required = false)  List<String> ids){
    if(ids == null || ids.isEmpty()){
      List<ProductParentModel> documents = mainService
      .findAll(ProductParentModel.class, collection);

      if(documents.isEmpty()){
        return ResponseEntity.status(404).body(documents);
      } else {
        return ResponseEntity.ok(documents);
      }
    } else {
      List<ProductParentModel> documents = mainService
      .findManyById("id", ids, ProductParentModel.class, collection);

      if(documents.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(documents);
      }
      return ResponseEntity.ok(documents);
    }
  }

  @GetMapping("/get/recursive")
  public ResponseEntity<Object> findOneByIdRecursive(@RequestParam @NotBlank String id){
    ProductParentModel parentDoc = mainService.findById(id, ProductParentModel.class, collection);

    if(parentDoc == null){
      String message = String.format("Document with ID \"%s\" does not exist !", id);
      logger.warn(message);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    } else {
      List<ProductVariationModel> variationDoc = mainService
      .findManyById("parentId", parentDoc.getId(), ProductVariationModel.class, variationCollection);

      parentDoc.setVariations(variationDoc);

      return ResponseEntity.ok(parentDoc);
    }
  }

  @GetMapping("/get/recursive/many")
  public ResponseEntity<Object> findManyByIdRecursive(@RequestParam @NotEmpty List<String> id){
    List<ProductParentModel> nonNullDocsList = productService.findManyRecursiveById(id);

    if(nonNullDocsList.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nonNullDocsList);
    }

    return ResponseEntity.ok(nonNullDocsList);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestBody @NotEmpty List<String> ids){
    List<ProductParentModel> removedDocs = mainService.deleteManyById(ids, ProductParentModel.class, collection);

    if(removedDocs.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(removedDocs);
    }
    return ResponseEntity.ok(removedDocs);
  }

  @DeleteMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestBody @NotEmpty List<String> ids){
    return productService.deleteRecursiveById(ids, collection);
  }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return ResponseEntity.ok(mainService.clearCollection(ProductParentModel.class, collection));
  }
  
}
