package com.server.canadapost.models;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class ShipPriceRequest {

  @JsonProperty private int weight;
  @JsonProperty private int length;
  @JsonProperty private int width;
  @JsonProperty private int height;
  @JsonProperty private String originPostalCode;
  @JsonProperty private String destinationPostalCode;

  public int getWeight(){
    return this.weight;
  }

  public int getLength(){
    return this.length;
  }

  public int getWidth(){
    return this.width;
  }

  public int getHeight(){
    return this.height;
  }

  public String getOriginPostalCode(){
    return this.originPostalCode;
  }

  public String getDestinationPostalCode(){
    return this.destinationPostalCode;
  }

  public ShipPriceRequest(){}

  public ShipPriceRequest(ShipPriceRequest body){
    this.weight = body.weight;
    this.length = body.length;
    this.width = body.width;
    this.height = body.height;
    this.originPostalCode = body.originPostalCode;
    this.destinationPostalCode = body.destinationPostalCode;
  }
}
