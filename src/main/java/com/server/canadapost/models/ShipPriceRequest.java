package com.server.canadapost.models;

import org.springframework.stereotype.Component;

@Component
public class ShipPriceRequest {

  public int weight;
  public int length;
  public int width;
  public int height;
  public String originPostalCode;
  public String destinationPostalCode;

  public ShipPriceRequest(){}

  public ShipPriceRequest(ShipPriceRequest requestBody){
    this.weight = requestBody.weight;
    this.length = requestBody.length;
    this.width = requestBody.width;
    this.height = requestBody.height;
    this.originPostalCode = requestBody.originPostalCode;
    this.destinationPostalCode = requestBody.destinationPostalCode;
  }
}
