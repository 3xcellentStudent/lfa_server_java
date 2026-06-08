package com.server.databases.mongodb.services.invoices.stripe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
// import com.common.models.stripe.invoices.StripeCheckoutSessionsWrapperModel;
import com.server.databases.mongodb.dto.DeleteManyById;
import com.server.databases.mongodb.dto.GetManyById;
import com.server.databases.mongodb.services.MongoDbMainService;

import jakarta.validation.Valid;

@Service
public class InvoicesStripeService {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MongoDbMainService mainService;

  // public ResponseEntity<Object> create(StripeCheckoutSessionsModel body){
  //   StripeCheckoutSessionsWrapperModel stripeWrapperModel = new StripeCheckoutSessionsWrapperModel(body);
  //   StripeCheckoutSessionsWrapperModel savedObject = mongoTemplate.save(stripeWrapperModel);

  //   return ResponseEntity.ok(savedObject);
  // }

  // public ResponseEntity<Object> updateOne(@Valid StripeCheckoutSessionsModel body){
  //   StripeCheckoutSessionsWrapperModel savedObject = mongoTemplate
  //   .findAndModify(null, null, StripeCheckoutSessionsWrapperModel.class);

  //   return ResponseEntity.ok(savedObject);
  // }

  // public ResponseEntity<Object> findManyById(GetManyById body){
  //   List<StripeCheckoutSessionsModel> savedObject = mainService
  //   .findManyById ("id", body.getId(), StripeCheckoutSessionsModel.class, body.getCollectionName());

  //   return ResponseEntity.ok(savedObject);
  // }

  // public ResponseEntity<Object> deleteManyById(DeleteManyById body){
  //   ResponseEntity<Object> savedObject = mainService.deleteManyById(body, StripeCheckoutSessionsModel.class);

  //   return ResponseEntity.ok(savedObject);
  // }
}
