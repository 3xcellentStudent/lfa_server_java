package com.common.models.stripe.invoices;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "stripe_invoices")
public class StripeCheckoutSessionsWrapperModel {
  @Id
  public String invoiceId;
  public StripeCheckoutSessionsModel stripeObject;
  public Delivery delivery;
  public OrderStatus orderStatus;
  public Boolean isReturned;
  public Long createdAt;
  public Long updatedAt;
  
  public class Delivery {
    public String status;
    public String trackingNumber;
    public String trackingUrl;
    public String carrier;
    public String price;
  }

  public class OrderStatus {
    public Boolean proccessing = false;
    public Boolean pendingForShipping = false;
    public Boolean shipped = false;
    public Boolean inLocalPostService = false;
    public Boolean lost = false;
    public Boolean delivered = false;
    public Boolean deliveryConfirmed = false;
  }

  public StripeCheckoutSessionsWrapperModel(){}

  public StripeCheckoutSessionsWrapperModel(StripeCheckoutSessionsModel data){
    long currentTime = System.currentTimeMillis();

    this.stripeObject = data;
    this.createdAt = currentTime;
    this.updatedAt = currentTime;
  } 

  public StripeCheckoutSessionsWrapperModel(StripeCheckoutSessionsWrapperModel data){
    this.invoiceId = data.invoiceId;
    this.stripeObject = data.stripeObject;
    this.delivery = data.delivery;
    this.orderStatus = data.orderStatus;
    this.isReturned = data.isReturned;
    this.createdAt = data.createdAt;
    this.updatedAt = data.updatedAt;
  }

  public String setInvoiceId(){
    this.invoiceId = UUID.randomUUID().toString();
    return this.invoiceId;
  }

  public String getInvoiceId(){
    return this.invoiceId;
  }

}
