package com.server.services.mongodb.controllers.reviews.oils;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.models.products.oils.OilsProductsModel;
import com.server.services.mongodb.repositories.products.oils.OilsProductRepository;

@RestController
@RequestMapping("/api/mongodb/reviews/oils")
@CrossOrigin("*")
public class OilsReviewsController {
  
  private OilsProductRepository repository;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody OilsProductsModel product){
    try {
      OilsProductsModel savedProduct = repository.save(product);
      return ResponseEntity.ok().body(savedProduct);
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody OilsProductsModel product){
    try {
      boolean existingDocument = repository.existsById(id);
      if(existingDocument){
        product.setId(id);
        OilsProductsModel updatedProduct = repository.save(product);
        return ResponseEntity.ok().body(updatedProduct);
      } else return ResponseEntity.status(404).body("Unable to update because this product does not exist !");
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }
  
  @GetMapping("/get/{id}")
  public ResponseEntity<Object> getById(@PathVariable String id){
    try {
      Optional<OilsProductsModel> product = repository.findById(id);
      if(product.isPresent()){
        return ResponseEntity.ok().body(product.get()); 
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Unable get product by ID from database !");
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> get(){
    try {
      List<OilsProductsModel> products = repository.findAll();
      if(products.isEmpty() == false){
        return ResponseEntity.ok().body(products); 
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Can't get all products from database !");
    }
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<Object> deleteById(@PathVariable String id){
    try {
      Optional<OilsProductsModel> existingDocument = repository.findById(id);
      if(existingDocument.isPresent()){
        repository.deleteById(id);
        return ResponseEntity.ok().body(existingDocument.get());
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<String> delete(){
    try {
      repository.deleteAll();
      return ResponseEntity.ok().body("Products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }
}
