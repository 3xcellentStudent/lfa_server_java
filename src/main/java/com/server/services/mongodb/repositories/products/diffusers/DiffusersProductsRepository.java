package com.server.services.mongodb.repositories.products.diffusers;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;

public interface DiffusersProductsRepository extends MongoRepository<DiffusersProductsModel, String>{}