package com.server.services.mongodb.controllers.products.diffusers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.helpers.bodies.RequestBodies.UpdateOneById;
import com.server.services.mongodb.repositories.products.diffusers.DiffusersProductsRepository;
import com.server.services.mongodb.services.products.diffusers.DiffusersProductsService;

@RestController
@RequestMapping("/api/mongodb/products/diffusers")
@CrossOrigin("*")
public class DiffusersProductsController {
  
  @Autowired
  private DiffusersProductsRepository productsRepository;
  @Autowired
  private DiffusersProductsService service;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = service.createOne(requestBodyString);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = service.updateOneById(requestBodyString);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }
  
  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    try {
      if(id == null || id.isEmpty()){
        ResponseEntity<Object> response = service.findAll();

        return response;
      } else {
        ResponseEntity<Object> response = service.findAllById(id);
  
        return response;
      }
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  @GetMapping("/get/recursive")
  public ResponseEntity<Object> findAllByIdRecursive(
    @RequestParam List<String> id, @RequestParam int fromIndex, @RequestParam int toIndex
  ){
    try {
      ResponseEntity<Object> response = service.findAllRecursive(id, fromIndex, toIndex);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = false) List<String> id){
    System.out.println(id);
    try {
      ResponseEntity<Object> response = service.deleteAllById(id);
      
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/delete/recursive")
  public ResponseEntity<Object> deleteAllByIdRecursive(@RequestParam(name = "id", required = false) List<String> id){
    try {
      ResponseEntity<Object> response = service.deleteRecursiveById(id);
      
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    try {
      productsRepository.deleteAll();

      return ResponseEntity.ok("All products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }
  
}