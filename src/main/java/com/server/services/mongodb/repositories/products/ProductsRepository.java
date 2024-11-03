package com.server.services.mongodb.repositories.products;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.products.ProductsModel;

public interface ProductsRepository extends MongoRepository<ProductsModel,String>{}
