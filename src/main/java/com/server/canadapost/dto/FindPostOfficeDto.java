package com.server.canadapost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindPostOfficeDto {
  @JsonProperty private String postalCode;
  @JsonProperty private String province;
  @JsonProperty private String city;
  @JsonProperty private String streetName;
  @JsonProperty private String maximum;

  public FindPostOfficeDto(String postalCode, String province, String city, String streetName, String maximum) {
    this.postalCode = postalCode;
    this.province = province;
    this.city = city;
    this.streetName = streetName;
    this.maximum = maximum;
  }

  public FindPostOfficeDto(){}

  public String getPostalCode() {
    return postalCode;
  }

  public String getProvince() {
    return province;
  }

  public String getCity() {
    return city;
  }

  public String getStreetName() {
    return streetName;
  }

  public String getMaximum() {
    return maximum;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public void setMaximum(String maximum) {
    this.maximum = maximum;
  }

}
