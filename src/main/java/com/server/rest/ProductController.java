package com.server.rest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.firebase.FirebaseService;

@CrossOrigin(origins = "http://localhost:3000/products")
@RestController
public class ProductController {
  
  @GetMapping("/products")
  public ResponseEntity<JSONObject> getAllProducts(@RequestParam String id){
    if(id.isEmpty()) return new FirebaseService().readCollection("");
    else return new FirebaseService().readDocument("products", id);
  };

  // @GetMapping("/products/id")
  // public ResponseEntity<JSONObject> getProductId(@RequestParam String id){
  //   if(id.isEmpty()) return 
  // };
}