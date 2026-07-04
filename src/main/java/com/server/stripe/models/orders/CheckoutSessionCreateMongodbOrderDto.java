package com.server.stripe.models.orders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;

@Document
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CheckoutSessionCreateMongodbOrderDto(String checkoutId, String invoiceId, String status, long expiresAt, long created){

  // @Id private String checkoutId;
  // private String invoiceId;
  // private String status;
  // private long expiresAt;
  // private long created;

  // public CheckoutSessionCreateMongodbOrderDto(CheckoutSessionObjectModel data){
    // this.checkoutId = data.getId();
    // this.invoiceId = data.getInvoiceId();
    // this.status = data.getStatus();
    // this.expiresAt = data.getExpiresAt();
    // this.created = data.getCreated();
  // }
}
