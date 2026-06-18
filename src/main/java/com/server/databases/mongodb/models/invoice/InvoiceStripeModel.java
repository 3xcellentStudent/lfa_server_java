package com.server.databases.mongodb.models.invoice;

import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InvoiceStripeModel {

  private String id;
  private String stripeCheckoutSessionId;
  private String number;
  private Long amountPaid;
  private Long total;
  private String currency;
  private Long shippingCost;
  private ShippingDetails shippingDetails;
  private Date paidAt;

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class ShippingDetails {
    private String recipientName;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
  }
}