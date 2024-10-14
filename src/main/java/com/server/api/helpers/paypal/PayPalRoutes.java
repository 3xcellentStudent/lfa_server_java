package com.server.api.helpers.paypal;

public class PayPalRoutes {
  public static final String PAYPAL_AUTH = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
  public static final String PAYPAL_CHECKOUT_ORDERS = "https://api-m.sandbox.paypal.com/v2/checkout/orders";
  // public static final String PAYPAL_CAPTURE_ORDER = "https://api-m.sandbox.paypal.com/v2/checkout/orders";
  // public static final String PAYPAL_DETAILS_ORDER = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

  public static final String ENDPOINT_CREATE_ORDER = "/create-order";
  public static final String ENDPOINT_CAPTURE_ORDER = "/capture-order";
  public static final String ENDPOINT_DETAILS_ORDER = "/details-order";

  public static final String ENDPOINT_PAYMENT_SUCCESS = "/payment-success";
  public static final String ENDPOINT_PAYMENT_CANCEL = "/payment-cancel";
}