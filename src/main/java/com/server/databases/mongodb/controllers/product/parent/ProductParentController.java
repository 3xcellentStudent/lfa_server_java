package com.server.databases.mongodb.controllers.product.parent;

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
import com.server.databases.mongodb.models.product.parent.ProductParentModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.parent.ProductParentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/mongodb/product")
@CrossOrigin("*")
@Validated
public class ProductParentController {

  @Value("${databases.mongodb.collections.products.main}")
  private String collection;
  @Value("${databases.mongodb.collections.products.variations}")
  private String variationCollection;

  private final Logger logger = LoggerFactory.getLogger(ProductParentController.class);

  @Autowired
  private ProductParentService productService;
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
  public ResponseEntity<Object> findAll(){
    List<ProductParentModel> documents = mainService.findAll(ProductParentModel.class, collection);
    return ResponseEntity.ok(documents);
  }

  @GetMapping("/get/id")
  public ResponseEntity<Object> findManyById(@RequestParam @NotEmpty List<String> ids){
    List<ProductParentModel> documentsList = mainService.findManyById("id", ids, ProductParentModel.class, collection);
    return ResponseEntity.ok(documentsList);
  }

  @GetMapping("/get/cascade/id")
  public ResponseEntity<Object> findOneByIdCascade(@RequestParam @NotBlank String id){
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

  @GetMapping("/get/cascade/many")
  public ResponseEntity<Object> findManyByIdCascade(@RequestParam @NotEmpty List<String> ids){
    List<ProductParentModel> nonNullDocsList = productService.findManyCascadeById(ids);

    if(nonNullDocsList.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nonNullDocsList);
    }

    return ResponseEntity.ok(nonNullDocsList);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestBody @NotEmpty List<String> ids){
    List<ProductParentModel> removedDocs = mainService.deleteManyById(ids, ProductParentModel.class, collection);
    return ResponseEntity.ok(removedDocs);
  }

  // @DeleteMapping("/delete/cascade")
  // public ResponseEntity<Object> deleteAllByIdCascade(@RequestBody @NotEmpty List<String> ids){
  //   return productService.deleteCascadeById(ids, collection);
  // }

  @DeleteMapping("/clear-col")
  public ResponseEntity<Object> clearCollection(){
    return ResponseEntity.ok(mainService.clearCollection(ProductParentModel.class, collection));
  }
  
}
