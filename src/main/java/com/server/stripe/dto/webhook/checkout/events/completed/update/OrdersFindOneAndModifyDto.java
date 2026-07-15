package com.server.stripe.dto.webhook.checkout.events.completed.update;

public record OrdersFindOneAndModifyDto(String checkoutId, String invoiceId, String status){

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