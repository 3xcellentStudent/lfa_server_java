package com.server.stripe.dto.webhook.completed.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;

// @JsonIgnoreProperties(ignoreUnknown = true)
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// public class StripeCheckoutWebhookEventDto {
  
//   public String id;
//   public String object;
//   public String apiVersion;
//   public long created;
//   public String type;
//   public EventData data;

//   @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//   public static class EventData {
//     public CheckoutSessionObjectModel object;
//   }

//   public CheckoutSessionObjectModel getInvoiceObject(){
//     return this.data.object;
//   }

// }

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StripeCheckoutWebhookEventDto(String id, String object, String apiVersion, long created, String type, EventData data) {
  
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record EventData(CheckoutSessionObjectModel object){
    @JsonCreator
      public EventData(@JsonProperty("object") CheckoutSessionObjectModel object) {
        this.object = object;
      }
  }

}