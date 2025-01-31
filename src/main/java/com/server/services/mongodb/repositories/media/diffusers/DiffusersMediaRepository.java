package com.server.services.mongodb.repositories.media.diffusers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;

@Repository
public interface DiffusersMediaRepository extends MongoRepository<DiffusersMediaModel, String>{
  
}
