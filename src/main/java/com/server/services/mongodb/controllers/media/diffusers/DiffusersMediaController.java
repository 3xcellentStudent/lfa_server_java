package com.server.services.mongodb.controllers.media.diffusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.services.mongodb.repositories.media.diffusers.DiffusersMediaRepository;
import com.server.services.mongodb.services.media.diffusers.DiffusersMediaService;

@RestController
@RequestMapping("/api/mongodb/media/diffusers")
@CrossOrigin("*")
public class DiffusersMediaController {

  @Autowired
  private DiffusersMediaRepository repository;

  private DiffusersMediaService service = new DiffusersMediaService();

  public ResponseEntity<Object> create(@RequestBody DiffusersMediaModel dataModel){
    try {
      ResponseEntity<Object> response = service.createOne(dataModel);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.err.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Failed to create object in database !");
    }
  }
    
}
