package com.server.stripe.services.checkout.webhooks.completed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.stripe.dto.webhook.completed.event.StripeCheckoutWebhookEventCompletedDto;

@Service
public class StripeCheckoutCompletedWebhookService {

  @Autowired
  private MongoDbMainService mongoDbMainService;
  
  public ResponseEntity<Object> updateInvoiceInDatabase(StripeCheckoutWebhookEventCompletedDto body){

    // mongoDbMainService.updateOneById(null, null, null, null);

    return ResponseEntity.ok(null);
  }

}
