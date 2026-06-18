package com.server.stripe.dto.webhook.completed.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Document
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// @JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CheckoutSessionObjectModel {

  @Id public String id;
  // public String object;
  // public AdaptivePricing adaptivePricing;
  // public Object afterExpiration;
  // public Boolean allowPromotionCodes;
  // public Long amountSubtotal;
  // public Long amountTotal;
  // public AutomaticTax automaticTax;
  // public String billingAddressCollection;
  // public BrandingSettings brandingSettings;
  // public String cancelUrl;
  // public String clientReferenceId;
  public String clientSecret;
  // public Object collectedInformation;
  // public Object consent;
  // public Object consentCollection;
  public Long created;
  // public String currency;
  // public Object currencyConversion;
  // public List<Object> customFields;
  // public CustomText customText;
  // public String customer;
  // public String customerAccount;
  // public String customerCreation;
  // public Object customerDetails;
  // public String customerEmail;
  // public List<Object> discounts;

  // @Indexed
  public Long expiresAt; // Из "expires_at", в Mongo запишется как expiresAt

  // public String integrationIdentifier;
  public String invoice;
  // public InvoiceCreation invoiceCreation;
  // public Boolean livemode;
  // public String locale;
  // public ManagedPayments managedPayments;
  // public Map<String, Object> metadata;
  // public String mode;
  // public Object originContext;
  // public String paymentIntent;
  // public String paymentLink;
  // public String paymentMethodCollection;
  // public Object paymentMethodConfigurationDetails;
  // public PaymentMethodOptions paymentMethodOptions;
  // public List<String> paymentMethodTypes;
  // public String paymentStatus;
  // public Object permissions;
  // public PhoneNumberCollection phoneNumberCollection;
  // public String recoveredFrom;
  // public String redirectOnCompletion;
  // public String returnUrl;
  // public SavedPaymentMethodOptions savedPaymentMethodOptions;
  // public String setupIntent;
  // public ShippingAddressCollection shippingAddressCollection;
  // public Object shippingCost;
  // public List<Object> shippingOptions;
  public String status;
  // public String submitType;
  // public String subscription;
  // public String successUrl;
  // public TotalDetails totalDetails;
  // public String uiMode;
  // public String url;
  // public Object walletOptions;

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class AdaptivePricing {
  //   public Boolean enabled;
  // }
  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class AutomaticTax {
  //   public Boolean enabled;
  //   public String liability;
  //   public String provider;
  //   public String status;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class BrandingSettings {
  //   public String backgroundColor;
  //   public String borderStyle;
  //   public String buttonColor;
  //   public String displayName;
  //   public String fontFamily;
  //   public Object icon;
  //   public Object logo;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class CustomText {
  //   public String afterSubmit;
  //   public String shippingAddress;
  //   public String submit;
  //   public String termsOfServiceAcceptance;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class InvoiceCreation {
  //   public Boolean enabled;
  //   public InvoiceData invoiceData;
  //   public static class InvoiceData {
  //     public List<String> accountTaxIds;
  //     public List<Object> customFields;
  //     public String description;
  //     public String footer;
  //     public Issuer issuer;
  //     public Map<String, Object> metadata;
  //     public Object renderingOptions;
  //     public static class Issuer {
  //       public String type;
  //     }
  //   }
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class ManagedPayments {
  //   public Boolean enabled;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class PaymentMethodOptions {
  //   public Card card;

  //   public static class Card {
  //     public String requestThreeDSecure;
  //   }
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class PhoneNumberCollection {
  //   public Boolean enabled;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class SavedPaymentMethodOptions {
  //   public List<String> allowRedisplayFilters;
  //   public String paymentMethodRemove;
  //   public String paymentMethodSave;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class ShippingAddressCollection {
  //   public List<String> allowedCountries;
  // }

  // @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  // public static class TotalDetails {
  //   public Long amountDiscount;
  //   public Long amountShipping;
  //   public Long amountTax;
  // }

  public String getClientSecret(){
    return this.clientSecret;
  }

  public String getStatus(){
    return this.status;
  }

  public String getId(){
    return this.id;
  }

  public Long getCreated(){
    return this.created;
  }

  public Long getExpiresAt(){
    return this.expiresAt;
  }

  public String getInvoiceId(){
    return this.invoice;
  }

}