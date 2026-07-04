package com.server.databases.mongodb.models.orders;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;

@Document
public class MainOrderModel {
 
  @Id private String checkoutId;
  private String invoiceId;
  private String status;
  private List<CheckoutCreateSessionClientRequestDto> productList;
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

  public List<CheckoutCreateSessionClientRequestDto> getProductList(){
    return this.productList;
  }

  public List<CheckoutCreateSessionClientRequestDto> setProductList(List<CheckoutCreateSessionClientRequestDto> newProductList){
    return this.productList = newProductList;
  }

  // Temporary solution. If it is microservices architecture then delete this constructor
  // public MainOrderModel(CheckoutSessionObjectModel data){
  //   this.checkoutId = data.id();
  //   this.invoiceId = data.invoice();
  //   this.status = data.status();
  //   this.productList = List.of();
  //   this.expiresAt = data.expiresAt();
  //   this.created = data.created();
  // }

  public MainOrderModel(CheckoutSessionObjectModel data, List<CheckoutCreateSessionClientRequestDto> productList){
    this.checkoutId = data.id();
    this.invoiceId = data.invoice();
    this.status = data.status();
    this.productList = productList;
    this.expiresAt = data.expiresAt();
    this.created = data.created();
  }

  public MainOrderModel(){}
  
}
