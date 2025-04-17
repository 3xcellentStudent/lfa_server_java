package com.server.databases.mongodb.services.invoices.stripe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.server.databases.mongodb.models.invoices.stripe.CheckoutSessionsModel;
import com.server.databases.mongodb.services.MainService;

public class InvoicesStripeService {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MainService mainService;

   public ResponseEntity<Object> createOne(String requestString){
    try {
      CheckoutSessionsModel requestObject = objectMapper.readValue(requestString, CheckoutSessionsModel.class);

      requestObject.setUpdated(requestObject.getCreated());

      CheckoutSessionsModel savedObject = mongoTemplate.save(requestObject);

      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      return ResponseEntity.status(503).body("Database unavailable");
    } catch (IllegalArgumentException error) {
      return ResponseEntity.status(400).body("Invalid data");
    } catch (Exception error) {
      return ResponseEntity.status(500).body("Unexpected error");
    }
  }

  public ResponseEntity<Object> findAllById(List<String> id){
    try {
      List<CheckoutSessionsModel> savedObject = mainService.findAllById("id", id, CheckoutSessionsModel.class);

      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      return ResponseEntity.status(503).body("Database unavailable");
    } catch (Exception error) {
      return ResponseEntity.status(500).body("Unexpected error");
    }
  }
}
