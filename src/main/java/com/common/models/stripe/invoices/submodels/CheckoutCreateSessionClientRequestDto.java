package com.common.models.stripe.invoices.submodels;

import java.util.List;

public class CheckoutCreateSessionClientRequestDto {

  public List<DataArray> data;

  public static class DataArray {
    public String productName;
    public String productId;
    public float unitAmount;
    public String quantity;
  }
  
  public CheckoutCreateSessionClientRequestDto(){}

  public CheckoutCreateSessionClientRequestDto(CheckoutCreateSessionClientRequestDto requestBody){
    this.data = requestBody.data;
  }

}
