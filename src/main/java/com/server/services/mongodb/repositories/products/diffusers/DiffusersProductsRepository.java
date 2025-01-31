package com.server.services.mongodb.repositories.products.diffusers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;

@Repository
public interface DiffusersProductsRepository extends MongoRepository<DiffusersProductsModel, String>{}