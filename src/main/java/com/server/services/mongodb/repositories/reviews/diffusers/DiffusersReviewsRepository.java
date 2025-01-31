package com.server.services.mongodb.repositories.reviews.diffusers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;

@Repository
public interface DiffusersReviewsRepository extends MongoRepository<DiffusersReviewsModel, String> {}