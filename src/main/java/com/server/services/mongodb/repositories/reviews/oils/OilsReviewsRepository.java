package com.server.services.mongodb.repositories.reviews.oils;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.reviews.oils.OilsReviewsModel;

public interface OilsReviewsRepository extends MongoRepository<OilsReviewsModel, String> {

}
