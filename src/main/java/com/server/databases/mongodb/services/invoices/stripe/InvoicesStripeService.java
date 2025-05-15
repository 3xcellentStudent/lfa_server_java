package com.server.databases.mongodb.services.invoices.stripe;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.common.models.stripe.invoices.StripeCheckoutSessionsModel;
import com.common.models.stripe.invoices.StripeCheckoutSessionsWrapperModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.server.databases.mongodb.services.MongoDbMainService;

@Service
public class InvoicesStripeService {

  private Logger logger = LoggerFactory.getLogger(InvoicesStripeService.class);
  
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MongoDbMainService mainService;

   public ResponseEntity<Object> createOne(String requestString){
    try {
      StripeCheckoutSessionsModel stripeDataObject = objectMapper.readValue(requestString, StripeCheckoutSessionsModel.class);
      StripeCheckoutSessionsWrapperModel stripeWrapperModel = new StripeCheckoutSessionsWrapperModel(stripeDataObject);
      StripeCheckoutSessionsWrapperModel savedObject = mongoTemplate.save(stripeWrapperModel);
      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      String message = "Database unavailable";
      logger.error(message, error);
      return ResponseEntity.status(503).body(message);
    } catch (IOException error){
      String message = "Error occured while Input/Output (I/O) proccess with JSON !";
      logger.error(message, error);
      return ResponseEntity.status(500).body(message);
    }
  }

  public ResponseEntity<Object> updateOne(String requestString, String queryString){
    try {
      // StripeCheckoutSessionsWrapperModel stripeWrapperModel = objectMapper
      // .readValue(requestString, StripeCheckoutSessionsWrapperModel.class);

      StripeCheckoutSessionsWrapperModel savedObject = mongoTemplate
      .findAndModify(null, null, StripeCheckoutSessionsWrapperModel.class);
      
      // StripeCheckoutSessionsWrapperModel savedObject = mongoTemplate.save(stripeWrapperModel);
      String responseString = objectMapper.writeValueAsString(savedObject);

      return ResponseEntity.ok(responseString);
    } catch (MongoSocketException | MongoTimeoutException error) {
      String message = "Database unavailable";
      logger.error(message, error);
      return ResponseEntity.status(503).body(message);
    } catch (IOException error){
      String message = "Error occured while Input/Output (I/O) proccess with JSON !";
      logger.error(message, error);
      return ResponseEntity.status(500).body(message);
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
