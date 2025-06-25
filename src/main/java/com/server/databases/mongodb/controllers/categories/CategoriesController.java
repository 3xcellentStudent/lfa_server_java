package com.server.databases.mongodb.controllers.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.UpdateOneByIdDto;
import com.server.databases.mongodb.models.global.GlobalDataModel;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
  
  // @Autowired
  // private MongoTemplate mongoTemplate;

  // @PostMapping("/categories/create/{category}")
  // public ResponseEntity<Object> createCategory(@PathVariable(required = true) String category){
  //   mongoTemplate.save(null);
  // }

  // @GetMapping("/categories")
  // public ResponseEntity<Object> getCategories(){
  // }

  // @GetMapping("/categories/{category}")
  // public ResponseEntity<Object> getCategory(@PathVariable(required = true) String category){
  // }

  // @PatchMapping("/categories/{category}/update-id")
  // public ResponseEntity<Object> categoryUpdateId(@RequestBody UpdateOneByIdDto body){
  // }

  // @DeleteMapping("/categories/{category}/delete-id")
  // public ResponseEntity<Object> categoryDeleteId(@RequestBody DeleteManyById body){
  // }
  
}
