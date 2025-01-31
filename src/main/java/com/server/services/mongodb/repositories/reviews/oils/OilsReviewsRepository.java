package com.server.services.mongodb.repositories.reviews.oils;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.reviews.oils.OilsReviewsModel;

@Repository
public interface OilsReviewsRepository extends MongoRepository<OilsReviewsModel, String> {

}
