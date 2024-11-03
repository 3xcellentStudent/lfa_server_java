package com.server.services.mongodb.repositories.invoices;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.server.services.mongodb.models.invoices.InvoicesModel;

public interface InvoicesRepository extends MongoRepository<InvoicesModel,String> {}
