package com.server.services.mongodb.controllers.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.models.products.ProductsModel;
import com.server.services.mongodb.repositories.products.ProductsRepository;

@RestController
@RequestMapping("/mongodb-products")
@CrossOrigin("*")
public class ProductsController {

  @Autowired
  private ProductsRepository repository;
  
  @PostMapping("/add")
  public ResponseEntity<ProductsModel> add(@RequestBody ProductsModel product){
    ProductsModel savedProduct = repository.save(product);
    return ResponseEntity.ok().body(savedProduct);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ProductsModel> updateById(@PathVariable String id, @RequestBody ProductsModel product){
    boolean existingDocument = repository.existsById(id);
    if(existingDocument){
      product.setId(id);
      ProductsModel updatedProduct = repository.save(product);
      return ResponseEntity.ok().body(updatedProduct);
    } else 
    return ResponseEntity.notFound().build();
  }
  
  @GetMapping("/get/{id}")
  public ResponseEntity<ProductsModel> getById(@PathVariable String id){
    return ResponseEntity.ok().body(repository.findById(id).get()); 
  }

  @GetMapping("/get")
  public ResponseEntity<List<ProductsModel>> get(){
    return ResponseEntity.ok().body(repository.findAll()); 
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<ProductsModel> deleteById(@PathVariable String id){
    Optional<ProductsModel> existingDocument = repository.findById(id);
    if(existingDocument.isPresent()){
      repository.deleteById(id);
      return ResponseEntity.ok().body(existingDocument.get());
    } else return ResponseEntity.notFound().build();
  }

  @GetMapping("/delete")
  public ResponseEntity<String> delete(){
    repository.deleteAll();
    return ResponseEntity.ok().build();
  }
}
