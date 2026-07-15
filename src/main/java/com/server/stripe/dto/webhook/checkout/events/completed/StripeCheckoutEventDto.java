package com.server.stripe.dto.webhook.checkout.events.completed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.server.stripe.dto.webhook.checkout.events.completed.object.StripeCheckoutCompletedDto;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StripeCheckoutEventDto(String id, String object, String apiVersion, long created, String type, EventData data) {
  
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record EventData(StripeCheckoutCompletedDto object){
    @JsonCreator
      public EventData(@JsonProperty("object") StripeCheckoutCompletedDto object) {
        this.object = object;
      }
  }

}