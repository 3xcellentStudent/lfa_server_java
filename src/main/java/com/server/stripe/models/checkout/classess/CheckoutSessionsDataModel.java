package com.server.stripe.models.checkout.classess;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.stripe.models.checkout.classess.CheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;

public class CheckoutSessionsDataModel {

  private String id;
  private String object;
  
  @JsonProperty("adaptive_pricing")
  private AdaptivePricing adaptivePricing;
  
  @JsonProperty("after_expiration")
  private AfterExpiration afterExpiration;
  
  @JsonProperty("allow_promotion_codes")
  private Boolean allowPromotionCodes;
  
  @JsonProperty("amount_subtotal")
  private Integer amountSubtotal;
  
  @JsonProperty("amount_total")
  private Integer amountTotal;
  
  @JsonProperty("automatic_tax")
  private AutomaticTax automaticTax;
  
  @JsonProperty("billing_address_collection")
  private String billingAddressCollection;
  
  @JsonProperty("cancel_url")
  private String cancelUrl;
  
  @JsonProperty("client_reference_id")
  private String clientReferenceId;
  
  @JsonProperty("client_secret")
  private String clientSecret;
  
  @JsonProperty("collected_information")
  private Object collectedInformation;
  
  private Object consent;
  
  @JsonProperty("consent_collection")
  private Object consentCollection;
  
  private Long created;
  private String currency;
  
  @JsonProperty("currency_conversion")
  private Object currencyConversion;
  
  @JsonProperty("custom_fields")
  private List<Object> customFields;
  
  @JsonProperty("custom_text")
  private CustomText customText;
  
  private Object customer;
  
  @JsonProperty("customer_creation")
  private String customerCreation;
  
  @JsonProperty("customer_details")
  private CustomerDetails customerDetails;
  
  @JsonProperty("customer_email")
  private String customerEmail;
  
  private List<Object> discounts;
  
  @JsonProperty("expires_at")
  private Long expiresAt;
  
  private String invoice;
  
  @JsonProperty("invoice_creation")
  private InvoiceCreation invoiceCreation;
  
  private Boolean livemode;
  private String locale;
  private Map<String, Object> metadata;
  private String mode;
  
  @JsonProperty("payment_intent")
  private String paymentIntent;
  
  @JsonProperty("payment_link")
  private String paymentLink;
  
  @JsonProperty("payment_method_collection")
  private String paymentMethodCollection;
  
  @JsonProperty("payment_method_configuration_details")
  private Object paymentMethodConfigurationDetails;
  
  @JsonProperty("payment_method_options")
  private PaymentMethodOptions paymentMethodOptions;
  
  @JsonProperty("payment_method_types")
  private List<String> paymentMethodTypes;
  
  @JsonProperty("payment_status")
  private String paymentStatus;
  
  private Object permissions;
  
  @JsonProperty("phone_number_collection")
  private PhoneNumberCollection phoneNumberCollection;
  
  @JsonProperty("recovered_from")
  private String recoveredFrom;
  
  @JsonProperty("redirect_on_completion")
  private String redirectOnCompletion;
  
  @JsonProperty("return_url")
  private String returnUrl;
  
  @JsonProperty("saved_payment_method_options")
  private Object savedPaymentMethodOptions;
  
  @JsonProperty("setup_intent")
  private String setupIntent;
  
  @JsonProperty("shipping_address_collection")
  private ShippingAddressCollection shippingAddressCollection;
  
  @JsonProperty("shipping_cost")
  private ShippingCost shippingCost;
  
  @JsonProperty("shipping_details")
  private Object shippingDetails;
  
  @JsonProperty("shipping_options")
  private List<Object> shippingOptions;
  
  private String status;
  
  @JsonProperty("submit_type")
  private Object submitType;
  
  private Object subscription;
  
  @JsonProperty("success_url")
  private Object successUrl;
  
  @JsonProperty("total_details")
  private TotalDetails totalDetails;
  
  @JsonProperty("ui_mode")
  private String uiMode;
  
  private Object url;

  public static class AdaptivePricing {
    public Boolean enabled;
  }

  public static class AfterExpiration {
    @JsonProperty("recovery")
    private Recovery recovery;

    public static class Recovery {
      @JsonProperty("allow_promotion_codes")
      public Boolean allowPromotionCodes;

      public Boolean enabled;

      @JsonProperty("expires_at")
      public Long expiresAt;

      public String url;
    }
  }

  public static class AutomaticTax {
    public Boolean enabled;
    public Liability liability;
    public String status;

    public static class Liability {
      public String type;
      public String account;
    }
  }
  
  public static class Consent {
    public String promotions;
    @JsonProperty("terms_of_service")
    public String terms_of_service;
  }

  public static class CollectedInformation {
    @JsonProperty("shipping_details")
    public ShippingDetails shippingDetails;

    public static class ShippingDetails {
      public Address address;
      public String name;

      public static class Address {
        public String city;
        public String country;
        public String line1;
        public String line2;

        @JsonProperty("postal_code")
        public String postal_code;

        public String state;
      }
    }
  }
  
  public static class CustomText {
    @JsonProperty("after_submit")
    public Object afterSubmit;

    @JsonProperty("shipping_address")
    public Object shippingAddress;

    public Object submit;

    @JsonProperty("terms_of_service_acceptance")
    public Object termsOfServiceAcceptance;
  }

  public static class CustomerDetails {
    public Address address;
    public String email;
    public String name;
    public String phone;

    @JsonProperty("tax_exempt")
    public String taxExempt;

    @JsonProperty("tax_ids")
    public Object taxIds;
  }

  public static class InvoiceCreation {
    public boolean enabled;
    @JsonProperty("invoice_data")
    public InvoiceData invoiceData;
  }

  public static class InvoiceData {
    @JsonProperty("account_tax_ids")
    public Object accountTaxIds;
    
    @JsonProperty("custom_fields")
    public Object customFields;
    
    public Object description;
    public Object footer;
    public Object issuer;
    public Map<String, Object> metadata;
    
    @JsonProperty("rendering_options")
    public Object renderingOptions;
  }

  public static class PaymentMethodOptions {
    public Card card;
  }

  public static class Card {
    @JsonProperty("request_three_d_secure")
    public String requestThreeDSecure;
  }

  public static class PhoneNumberCollection {
    public Boolean enabled;
  }

  public static class ShippingAddressCollection {
    @JsonProperty("allowed_countries")
    public List<String> allowedCountries;
  }
  
  public static class ShippingCost {
    @JsonProperty("amount_subtotal")
    public Integer amountSubtotal;

    @JsonProperty("amount_tax")
    public Integer amountTax;

    @JsonProperty("amount_total")
    public Integer amountTotal;

    @JsonProperty("shipping_rate")
    public String shippingRate;

    public Integer taxes;

    public static class Taxes {
      public Integer amount;
      public Object rate;
    }
  }

  public static class TotalDetails {
    @JsonProperty("amount_discount")
    public Integer amountDiscount;

    @JsonProperty("amount_shipping")
    public Integer amountShipping;

    @JsonProperty("amount_tax")
    public Integer amountTax;

    public Object breakdown;
  }

  public CheckoutSessionsDataModel(){}

  public CheckoutSessionsDataModel(CheckoutSessionsDataModel requestBody){
    this.metadata = requestBody.metadata;
    this.livemode = requestBody.livemode;
    this.amountTotal = requestBody.amountTotal;
    this.mode = requestBody.mode;
    this.customerCreation = requestBody.customerCreation;
    this.discounts = requestBody.discounts;
    this.clientReferenceId = requestBody.clientReferenceId;
    this.id = requestBody.id;
    this.clientSecret = requestBody.clientSecret;
    this.billingAddressCollection = requestBody.billingAddressCollection;
    this.shippingAddressCollection = requestBody.shippingAddressCollection;
    this.created = requestBody.created;
    this.customFields = requestBody.customFields;
    this.shippingOptions = requestBody.shippingOptions;
    this.consent = requestBody.consent;
    this.recoveredFrom = requestBody.recoveredFrom;
    this.submitType = requestBody.submitType;
    this.paymentMethodConfigurationDetails = requestBody.paymentMethodConfigurationDetails;
    this.customText = requestBody.customText;
    this.customerEmail = requestBody.customerEmail;
    this.adaptivePricing = requestBody.adaptivePricing;
    this.paymentIntent = requestBody.paymentIntent;
    this.collectedInformation = requestBody.collectedInformation;
    this.cancelUrl = requestBody.cancelUrl;
    this.uiMode = requestBody.uiMode;
    this.amountSubtotal = requestBody.amountSubtotal;
    this.object = requestBody.object;
    this.status = requestBody.status;
    this.paymentMethodCollection = requestBody.paymentMethodCollection;
    this.redirectOnCompletion = requestBody.redirectOnCompletion;
    this.shippingDetails = requestBody.shippingDetails;
    this.subscription = requestBody.subscription;
    this.locale = requestBody.locale;
    this.paymentLink = requestBody.paymentLink;
    this.savedPaymentMethodOptions = requestBody.savedPaymentMethodOptions;
    this.consentCollection = requestBody.consentCollection;
    this.expiresAt = requestBody.expiresAt;
    this.currencyConversion = requestBody.currencyConversion;
    this.phoneNumberCollection = requestBody.phoneNumberCollection;
    this.returnUrl = requestBody.returnUrl;
    this.currency = requestBody.currency;
    this.paymentMethodOptions = requestBody.paymentMethodOptions;
    this.successUrl = requestBody.successUrl;
    this.setupIntent = requestBody.setupIntent;
    this.invoiceCreation = requestBody.invoiceCreation;
    this.shippingCost = requestBody.shippingCost;
    this.paymentMethodTypes = requestBody.paymentMethodTypes;
    this.totalDetails = requestBody.totalDetails;
    this.paymentStatus = requestBody.paymentStatus;
    this.permissions = requestBody.permissions;
    this.url = requestBody.url;
    this.automaticTax = requestBody.automaticTax;
    this.invoice = requestBody.invoice;
    this.customer = requestBody.customer;
  }

  public String getId(){
    return this.id;
  }
  public String getClientSecret(){
    return this.clientSecret;
  }
  public Map<String, Object> getMetadata(){
    return this.metadata;
  }
  public int getAmountTotal(){
    return this.amountTotal;
  }
  public int getAmountSubtotal(){
    return this.amountSubtotal;
  }
  public long getCreated(){
    return this.created;
  }
  public long getExpiresAt(){
    return this.expiresAt;
  }
  public String getStatus(){
    return this.status;
  }
  public String getReturnUrl(){
    return this.returnUrl;
  }
  public String getCurrency(){
    return this.currency;
  }
  public String getPaymentStatus(){
    return this.paymentStatus;
  }
  public String getPaymentIntentId(){
    return this.paymentIntent;
  }
  public CustomerDetails getCustomerDetails(){
    return this.customerDetails;
  }
}