package com.server.databases.mongodb.models.orders;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;

@Document
public class MainOrderModel {
 
  @Id private String checkoutId;
  private String invoiceId;
  private String status;
  private Long expiresAt;
  private Long created;

  public String getCheckoutId(){
    return this.checkoutId;
  }

  public String getInvoiceId(){
    return this.invoiceId;
  }

  public String getStatus(){
    return this.status;
  }

  public Long getExpiresAt(){
    return this.expiresAt;
  }

  public Long getCreated(){
    return this.created;
  }

  // Temporary solution. If it is microservices architecture then delete this constructor
  public MainOrderModel(CheckoutSessionObjectModel data){
    this.checkoutId = data.getId();
    this.invoiceId = data.getInvoiceId();
    this.status = data.getStatus();
    this.expiresAt = data.getExpiresAt();
    this.created = data.getCreated();
  }

  public MainOrderModel(){}
  
}
