package com.server.databases.mongodb.controllers.product.variation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.product.variation.ProductVariationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/mongodb/product/variation")
@CrossOrigin("*")
@Validated
public class ProductVariationController {

  private static final Set<String> PAGINATION_FIELDS = Set.of(
    "_id", 
    "parentId", 
    "createdAt"
  );

  
  @Value("${databases.mongodb.collections.products.variations}")
  private String variationCollection;
  @Value("${databases.mongodb.collections.products.main}")
  private String productCollection;

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MongoDbMainService mainService;
  @Autowired
  private ProductVariationService productVariationService;

  @PostMapping("/create/parent-id")
  public ResponseEntity<Object> createByParentId(@Valid @RequestBody CreateVariationByParentId body){
    ProductVariationModel insertedDoc = productVariationService.createByParentId(body);

    if(insertedDoc == null){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent with ID " + body.parentId() + " was not found !");
    }

    return ResponseEntity.ok(insertedDoc);
  }

  @GetMapping("/get/parent-id")
  public ResponseEntity<Object> getByParentId(@RequestParam @NotBlank String parentId){
    Query existQuery = Query.query(Criteria.where("_id").is(parentId));
    boolean isExists = mongoTemplate.exists(existQuery, ProductVariationModel.class, productCollection);
    
    if(!isExists){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document with ID: \"" + parentId + "\" was not found");
    }
    List<ProductVariationModel> variations = mainService.findManyById("parentId", parentId, ProductVariationModel.class, variationCollection);
    return ResponseEntity.ok(variations);
  }

  @GetMapping("/get/id")
  public ResponseEntity<Object> getManyById(@RequestParam @NotEmpty List<String> ids){
    List<ProductVariationModel> variations = mainService.findManyById("id", ids, ProductVariationModel.class, variationCollection);
    return ResponseEntity.ok(variations);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findByPage(
    @RequestParam(defaultValue = "0") @Min(0) int page, 
    @RequestParam(defaultValue = "20") @Min(1) int size, 
    @RequestParam(defaultValue = "_id") String selector
  ){
    if(!PAGINATION_FIELDS.contains(selector)){
      String message = "\"Selector\" must be same as: " + String.join(", ", PAGINATION_FIELDS);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    int validatedSize = Math.min(size, 50);

    Page<ProductVariationModel> variations = mainService.findByPage(page, validatedSize, selector, ProductVariationModel.class, variationCollection);

    return ResponseEntity.ok(variations);
  }

  @PatchMapping("/update/id")
  public ResponseEntity<Object> updateOneById(@Valid @RequestBody UpdateOneByIdDto body){
    ProductVariationModel modifiedDoc = mainService.updateOneById(body, ProductVariationModel.class, variationCollection);

    if(modifiedDoc == null){
      String message = "Document with ID: \"" + body.id() + "\" was not found";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    } else {
      return ResponseEntity.ok(modifiedDoc);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteById(@RequestBody @NotEmpty List<String> ids){
    List<ProductVariationModel> deletedVariations = productVariationService.deteleManyById(ids);

    return ResponseEntity.ok(deletedVariations);
  }
  
  @DeleteMapping("/clear")
  public ResponseEntity<Object> clearCollection(){
    return ResponseEntity.ok(mainService.clearCollection(ProductVariationModel.class, variationCollection));
  }

}
