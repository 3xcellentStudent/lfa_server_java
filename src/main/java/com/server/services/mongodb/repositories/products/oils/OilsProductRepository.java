package com.server.services.mongodb.repositories.products.oils;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.products.oils.OilsProductsModel;

public interface OilsProductRepository extends MongoRepository<OilsProductsModel, String>{}