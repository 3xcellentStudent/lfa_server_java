package com.common.models.stripe.invoices;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "stripe_invoices")
public class StripeCheckoutSessionsModel {
  public String id;
  public String object;

  @JsonProperty("api_version")
  public String apiVersion;
  
  @JsonProperty("data")
  public DataObject data;

  public Long created;
  public Boolean livemode;

  @JsonProperty("pending_webhooks")
  public Integer pendingWebhooks;

  @JsonProperty("request")
  public RequestObject request;
  public String type;

  public static class DataObject {
    @JsonProperty("object")
    public StripeCheckoutSessionsDataModel object;
  }

  public static class RequestObject {
    public String id;
    @JsonProperty("idempotency_key")
    public String idempotencyKey;
  }

  public String getId(){
    return this.id;
  }
  public Long getCreated(){
    return this.created;
  }
  public DataObject getData(){
    return this.data;
  }
  public Boolean getLivemode(){
    return this.livemode;
  }
  public Integer getPendingWebhooks(){
    return this.pendingWebhooks;
  }
  public RequestObject getRequest(){
    return this.request;
  }
  public String getType(){
    return this.type;
  }
  public String getApiVersion(){
    return this.apiVersion;
  }
  public String getObject(){
    return this.object;
  }
  // public void setData(CheckoutSessionsDataModel data){
  //   this.data.object = data;
  // }

  public StripeCheckoutSessionsModel(){}

  public StripeCheckoutSessionsModel(StripeCheckoutSessionsModel data){
    this.id = data.id;
    this.object = data.object;
    this.apiVersion = data.apiVersion;
    this.data = data.data;
    this.created = data.created;
    this.livemode = data.livemode;
    this.pendingWebhooks = data.pendingWebhooks;
    this.request = data.request;
    this.type = data.type;
    this.object = data.object;
  }

}
