package com.server.services.mongodb.repositories.invoices;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.services.mongodb.models.invoices.InvoicesModel;

@Repository
public interface InvoicesRepository extends MongoRepository<InvoicesModel,String> {}
