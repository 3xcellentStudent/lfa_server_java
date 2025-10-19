package com.server.canadapost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PickupRequestPriceDto {
  @JsonProperty private String contractId;
  @JsonProperty private String date;
  @JsonProperty private String priorityFlag;
  @JsonProperty private String alternateAddressPostalCode;

  public String getContractId() {
    return contractId;
  }

  public String getDate() {
    return date;
  }

  public String getPriorityFlag() {
    return priorityFlag;
  }

  public String getAlternateAddressPostalCode() {
    return alternateAddressPostalCode;
  }

  public PickupRequestPriceDto(String contractId, String date, String priorityFlag, String alternateAddressPostalCode) {
    this.contractId = contractId;
    this.date = date;
    this.priorityFlag = priorityFlag;
    this.alternateAddressPostalCode = alternateAddressPostalCode;
  }

  public PickupRequestPriceDto() {
  }

}
