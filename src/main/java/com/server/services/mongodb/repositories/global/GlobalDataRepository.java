package com.server.services.mongodb.repositories.global;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.server.services.mongodb.models.global.GlobalDataModel;

public interface GlobalDataRepository extends MongoRepository<GlobalDataModel, String> {}
