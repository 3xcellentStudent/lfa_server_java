package com.server.databases.mongodb.models.invoices.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.stripe.models.checkout.dtos.CheckoutSessionsDataModel;

public class CheckoutSessionsModel {
  private String id;
  private DataObject data;
  
  @JsonProperty("api_version")
  private String api_version;

  private Long created;
  private Long updated;
  private Boolean livemode;

  @JsonProperty("pending_webhooks")
  private Object pendingWebhooks;

  private Object request;
  private String type;

  public static class Request {
    public String id;

    @JsonProperty("idempotency_key")
    public Object idempotencyKey;
  }

  public static class DataObject {
    public CheckoutSessionsDataModel object;
  }

  public String getId(){
    return this.id;
  }
  public Long getCreated(){
    return this.created;
  }
  public Long getUpdated(){
    return this.updated;
  }
  public CheckoutSessionsDataModel getData(){
    return this.data.object;
  }
  public Boolean getLivemode(){
    return this.livemode;
  }
  public Object getPendingWebhooks(){
    return this.pendingWebhooks;
  }
  public Object getRequest(){
    return this.request;
  }
  public String getType(){
    return this.type;
  }

  public void setUpdated(long newTime){
    this.updated = newTime;
  }
  // public void setData(CheckoutSessionsDataModel data){
  //   this.data.object = data;
  // }

}