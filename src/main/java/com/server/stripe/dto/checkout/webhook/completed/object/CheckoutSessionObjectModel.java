package com.server.stripe.dto.checkout.webhook.completed.object;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CheckoutSessionObjectModel {
  public String id;
  public String object;
  public Long amountSubtotal;
  public Long amountTotal;
  public String currency;
  public String customer;
  public String invoice;
  public String paymentIntent;
  public String paymentStatus;
  public String status;
  public String uiMode;
  public String returnUrl;
  
  public CollectedInformation collectedInformation;
  public CustomerDetails customerDetails;
  public TotalDetails totalDetails;
  public Map<String, String> metadata;
  public List<String> paymentMethodTypes;

  public static class CollectedInformation {
    public ShippingDetails shippingDetails;
  }

  public static class ShippingDetails {
    public Address address;
    public String name;
  }

  public static class CustomerDetails {
    public Address address;
    public String name;
    public String email;
  }

  public static class Address {
    public String line1;
    public String line2;
    public String city;
    public String state;
    public String postalCode;
    public String country;
  }

  public static class TotalDetails {
    public long amountDiscount;
    public long amountShipping;
    public long amountTax;
  }

}
