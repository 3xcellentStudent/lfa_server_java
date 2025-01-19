package com.server.services.mongodb.repositories.reviews.diffusers;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;

public interface DiffusersReviewsRepository extends MongoRepository<DiffusersReviewsModel, String> {
  
}
