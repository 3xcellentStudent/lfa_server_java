package com.server.services.mongodb.repositories.global;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.global.GlobalDataModel;

@Repository
public interface GlobalDataRepository extends MongoRepository<GlobalDataModel, String> {}
