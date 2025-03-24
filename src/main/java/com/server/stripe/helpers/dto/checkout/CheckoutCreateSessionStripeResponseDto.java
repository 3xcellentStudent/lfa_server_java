package com.server.stripe.helpers.dto.checkout;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutCreateSessionStripeResponseDto {
  private String id;
  private String object;
  
  @JsonProperty("adaptive_pricing")
  private AdaptivePricing adaptivePricing;
  
  @JsonProperty("after_expiration")
  private Object afterExpiration;
  
  @JsonProperty("allow_promotion_codes")
  private Object allowPromotionCodes;
  
  @JsonProperty("amount_subtotal")
  private Integer amountSubtotal;
  
  @JsonProperty("amount_total")
  private Integer amountTotal;
  
  @JsonProperty("automatic_tax")
  private AutomaticTax automaticTax;
  
  @JsonProperty("billing_address_collection")
  private Object billingAddressCollection;
  
  @JsonProperty("cancel_url")
  private Object cancelUrl;
  
  @JsonProperty("client_reference_id")
  private Object clientReferenceId;
  
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
  private Object customerDetails;
  
  @JsonProperty("customer_email")
  private Object customerEmail;
  
  private List<Object> discounts;
  
  @JsonProperty("expires_at")
  private Long expiresAt;
  
  private Object invoice;
  
  @JsonProperty("invoice_creation")
  private InvoiceCreation invoiceCreation;
  
  private Boolean livemode;
  private Object locale;
  private Map<String, Object> metadata;
  private String mode;
  
  @JsonProperty("payment_intent")
  private Object paymentIntent;
  
  @JsonProperty("payment_link")
  private Object paymentLink;
  
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
  private Object recoveredFrom;
  
  @JsonProperty("redirect_on_completion")
  private String redirectOnCompletion;
  
  @JsonProperty("return_url")
  private String returnUrl;
  
  @JsonProperty("saved_payment_method_options")
  private Object savedPaymentMethodOptions;
  
  @JsonProperty("setup_intent")
  private Object setupIntent;
  
  @JsonProperty("shipping_address_collection")
  private ShippingAddressCollection shippingAddressCollection;
  
  @JsonProperty("shipping_cost")
  private Object shippingCost;
  
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
    public boolean enabled;
  }

  public static class AutomaticTax {
    public boolean enabled;
    public Object liability;
    public Object status;
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
    public boolean enabled;
  }

  public static class ShippingAddressCollection {
    @JsonProperty("allowed_countries")
    public List<String> allowedCountries;
  }

  public static class TotalDetails {
    @JsonProperty("amount_discount")
    public int amountDiscount;

    @JsonProperty("amount_shipping")
    public int amountShipping;

    @JsonProperty("amount_tax")
    public int amountTax;
  }

  public CheckoutCreateSessionStripeResponseDto(){}

  public CheckoutCreateSessionStripeResponseDto(CheckoutCreateSessionStripeResponseDto requestBody){
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
}