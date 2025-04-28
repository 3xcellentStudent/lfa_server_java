package com.server.databases.mongodb.services.invoices.stripe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.server.databases.mongodb.services.MongoDbMainService;

@Service
public class InvoicesStripeService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MongoDbMainService mainService;

   public ResponseEntity<Object> createOne(String requestString){
    try {
      StripeCheckoutSessionsModel requestObject = objectMapper.readValue(requestString, StripeCheckoutSessionsModel.class);
      StripeCheckoutSessionsModel savedObject = mongoTemplate.save(requestObject);
      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      error.printStackTrace();
      return ResponseEntity.status(503).body("Database unavailable");
    } catch (IllegalArgumentException error) {
      error.printStackTrace();
      return ResponseEntity.status(400).body("Invalid data");
    } catch (Exception error) {
      error.printStackTrace();
      return ResponseEntity.status(500).body("Unexpected error");
    }
  }

  public ResponseEntity<Object> findAllById(List<String> id){
    try {
      List<StripeCheckoutSessionsModel> savedObject = mainService.findAllById("id", id, StripeCheckoutSessionsModel.class);

      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      return ResponseEntity.status(503).body("Database unavailable");
    } catch (Exception error) {
      return ResponseEntity.status(500).body("Unexpected error");
    }
  }

  public ResponseEntity<Object> deleteAllById(List<String> id){
    try {
      ResponseEntity<Object> savedObject = mainService.deleteAllById(id, StripeCheckoutSessionsModel.class);

      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      return ResponseEntity.status(503).body("Database unavailable");
    } catch (Exception error) {
      return ResponseEntity.status(500).body("Unexpected error");
    }
  }
}
