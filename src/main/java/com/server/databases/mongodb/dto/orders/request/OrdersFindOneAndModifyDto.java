package com.server.databases.mongodb.dto.orders.request;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;

public record OrdersFindOneAndModifyDto(String checkoutId, String invoiceId, String status){
  
  // @JsonProperty private String checkoutId;
  // @JsonProperty private String invoiceId;
  // @JsonProperty private String status;

  // public String getCheckoutId(){
  //   return this.checkoutId;
  // }

  // public String getInvoiceId(){
  //   return this.invoiceId;
  // }

  // public String getStatus(){
  //   return this.status;
  // }

  // public OrdersFindOneAndModifyDto(CheckoutSessionObjectModel data){
  //   this.checkoutId = data.getId();
  //   this.invoiceId = data.getInvoiceId();
  //   this.status = data.getStatus();
  // }
  
  // public OrdersFindOneAndModifyDto(){}

}
