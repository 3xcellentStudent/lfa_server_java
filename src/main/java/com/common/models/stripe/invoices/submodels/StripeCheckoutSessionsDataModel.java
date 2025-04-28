package com.common.models.stripe.invoices.submodels;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class StripeCheckoutSessionsDataModel {

  public String id;
  public String object;

  // @JsonProperty("api_version")
  // public AdaptivePricing apiVersion;
  
  @JsonProperty("adaptive_pricing")
  public AdaptivePricing adaptivePricing;
  
  @JsonProperty("after_expiration")
  public AfterExpiration afterExpiration;
  
  @JsonProperty("allow_promotion_codes")
  public Boolean allowPromotionCodes;
  
  @JsonProperty("amount_subtotal")
  public Integer amountSubtotal;
  
  @JsonProperty("amount_total")
  public Integer amountTotal;
  
  @JsonProperty("automatic_tax")
  public AutomaticTax automaticTax;
  
  @JsonProperty("billing_address_collection")
  public String billingAddressCollection;
  
  @JsonProperty("cancel_url")
  public String cancelUrl;
  
  @JsonProperty("client_reference_id")
  public String clientReferenceId;
  
  @JsonProperty("client_secret")
  public String clientSecret;
  
  @JsonProperty("collected_information")
  public CollectedInformation collectedInformation;
  
  public Object consent;
  
  @JsonProperty("consent_collection")
  public Object consentCollection;
  
  public Long created;
  public String currency;
  
  @JsonProperty("currency_conversion")
  public Object currencyConversion;
  
  @JsonProperty("custom_fields")
  public List<Object> customFields;
  
  @JsonProperty("custom_text")
  public CustomText customText;
  
  public Object customer;
  
  @JsonProperty("customer_creation")
  public String customerCreation;
  
  @JsonProperty("customer_details")
  public CustomerDetails customerDetails;
  
  @JsonProperty("customer_email")
  public String customerEmail;
  
  public List<Object> discounts;
  
  @JsonProperty("expires_at")
  public Long expiresAt;
  
  public String invoice;
  
  @JsonProperty("invoice_creation")
  public InvoiceCreation invoiceCreation;
  
  public Boolean livemode;
  public String locale;
  public Map<String, Object> metadata;
  public String mode;
  
  @JsonProperty("payment_intent")
  public String paymentIntent;
  
  @JsonProperty("payment_link")
  public String paymentLink;
  
  @JsonProperty("payment_method_collection")
  public String paymentMethodCollection;
  
  @JsonProperty("payment_method_configuration_details")
  public Object paymentMethodConfigurationDetails;
  
  @JsonProperty("payment_method_options")
  public PaymentMethodOptions paymentMethodOptions;
  
  @JsonProperty("payment_method_types")
  public List<String> paymentMethodTypes;
  
  @JsonProperty("payment_status")
  public String paymentStatus;
  
  public Object permissions;
  
  @JsonProperty("phone_number_collection")
  public PhoneNumberCollection phoneNumberCollection;
  
  @JsonProperty("recovered_from")
  public String recoveredFrom;
  
  @JsonProperty("redirect_on_completion")
  public String redirectOnCompletion;
  
  @JsonProperty("return_url")
  public String returnUrl;
  
  @JsonProperty("saved_payment_method_options")
  public Object savedPaymentMethodOptions;
  
  @JsonProperty("setup_intent")
  public String setupIntent;
  
  @JsonProperty("shipping_address_collection")
  public ShippingAddressCollection shippingAddressCollection;
  
  @JsonProperty("shipping_cost")
  public ShippingCost shippingCost;
  
  @JsonProperty("shipping_details")
  public Object shippingDetails;
  
  @JsonProperty("shipping_options")
  public List<Object> shippingOptions;
  
  public String status;
  
  @JsonProperty("submit_type")
  public Object submitType;
  
  public Object subscription;
  
  @JsonProperty("success_url")
  public Object successUrl;
  
  @JsonProperty("total_details")
  public TotalDetails totalDetails;
  
  @JsonProperty("ui_mode")
  public String uiMode;
  
  public Object url;

  public static class AdaptivePricing {
    public Boolean enabled;
  }

  public static class AfterExpiration {
    @JsonProperty("recovery")
    public Recovery recovery;

    public static class Recovery {
      @JsonProperty("allow_promotion_codes")
      public Boolean allowPromotionCodes;

      public Boolean enabled;

      @JsonProperty("expires_at")
      public Long expiresAt;

      public String url;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
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
        public String postalCode;

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

  public StripeCheckoutSessionsDataModel(){}

  public StripeCheckoutSessionsDataModel(StripeCheckoutSessionsDataModel requestBody){
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