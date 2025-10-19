package com.server.canadapost.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

@Component
public class ShipPriceDto {

  @JsonProperty @NotBlank private int weight;
  @JsonProperty @NotBlank private int length;
  @JsonProperty @NotBlank private int width;
  @JsonProperty @NotBlank private int height;
  @JsonProperty @NotBlank private String originPostalCode;
  @JsonProperty @NotBlank private String destinationPostalCode;

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

  public ShipPriceDto(){}

  public ShipPriceDto(ShipPriceDto body){
    this.weight = body.getWeight();
    this.length = body.getLength();
    this.width = body.getWeight();
    this.height = body.getHeight();
    this.originPostalCode = body.getOriginPostalCode();
    this.destinationPostalCode = body.getDestinationPostalCode();
  }
}
