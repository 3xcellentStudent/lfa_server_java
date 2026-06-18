package com.server.databases.mongodb.dto.orders.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersGetOneByIdDto {
  
  @JsonProperty private String checkoutId;
  @JsonProperty private String expiresAt;

  public String getCheckoutId(){
    return this.checkoutId;
  }

  public String getExpiresAt(){
    return this.expiresAt;
  }

}
