package com.server.services.mongodb.controllers.products.diffusers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.services.mongodb.repositories.products.diffusers.DiffusersProductsRepository;
import com.server.services.mongodb.services.DiffusersService;

@RestController
@RequestMapping("/api/mongodb/products/diffusers")
@CrossOrigin("*")
public class DiffusersProductsController {
  
  private DiffusersProductsRepository productRepository;
  private DiffusersService service;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody DiffusersProductsModel productModel){
    try {
      ResponseEntity<Object> response = service.createOne(productModel);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<Object> update(@PathVariable String _id, @RequestBody DiffusersProductsModel productModel){
    try {
      ResponseEntity<Object> response = service.updateOneById(_id, productModel);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }
  
  @GetMapping("/get/{id}")
  public ResponseEntity<Object> get(@PathVariable Iterable<String> _ids){
    try {
      ResponseEntity<Object> response = service.getById(_ids);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  // @GetMapping("/get")
  // public ResponseEntity<Object> getAll(@PathVariable Iterable<String> _id){
  //   try {
  //     ResponseEntity<Object> products = service.getManyById(_id);

  //     return products;
  //   } catch(Exception error){
  //     error.printStackTrace();
  //     System.out.println(error.getMessage());
  //     return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
  //   }
  // }

  @GetMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Iterable<String> _ids){
    try {
      boolean isExist = productRepository.existsById(_ids);
      if(isExist){
        productRepository.deleteById(_ids);

        return ResponseEntity.ok("Product successfully deleted");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/clear-db")
  public ResponseEntity<String> clearDatabase(){
    try {
      productRepository.deleteAll();

      return ResponseEntity.ok("All products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }
}