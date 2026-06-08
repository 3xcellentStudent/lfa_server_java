package com.server.stripe.dto.checkout.webhook.completed.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.server.stripe.dto.checkout.webhook.completed.object.CheckoutSessionObjectModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StripeCheckoutWebhookEventCompletedDto {
  
  public String id;
  public String object;
  public String apiVersion;
  public long created;
  public String type;
  public EventData data;

  public static class EventData {
    public CheckoutSessionObjectModel object;
  }

}