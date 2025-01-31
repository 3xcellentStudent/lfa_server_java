package com.server.services.mongodb.repositories.products.oils;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.products.oils.OilsProductsModel;

@Repository
public interface OilsProductRepository extends MongoRepository<OilsProductsModel, String>{}