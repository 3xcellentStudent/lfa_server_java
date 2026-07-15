package com.server.stripe.dto.webhook.checkout.events.expired;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StripeCheckoutExpiredEvent(String id, String object, String apiVersion, long created, String type, EventData data){
  
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record EventData(StripeCheckoutExpiredDto object){
    @JsonCreator
      public EventData(@JsonProperty("object") StripeCheckoutExpiredDto object){
        this.object = object;
      }
  }

}
