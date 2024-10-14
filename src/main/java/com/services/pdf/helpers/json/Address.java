package com.services.pdf.helpers.json;

import java.lang.reflect.Field;

import org.json.JSONObject;

public class Address {
  public String address_line_1;
  public String address_line_2;
  public String admin_area_2;
  public String admin_area_1;
  public String postal_code;
  public String country_code;

  public Address(JSONObject shipping){
    setAddress(shipping);
  }

  private JSONObject getAddressJsonBody(JSONObject shipping){
    return shipping.getJSONObject("shipping").getJSONObject("address");
  }

  private void setAddress(JSONObject shipping){
    try {
      JSONObject addressBody = getAddressJsonBody(shipping);
      
      for(Object key : addressBody.names()){
        String fieldName = key + "";
        String fieldValue = addressBody.getString(fieldName);
  
        Field field = this.getClass().getDeclaredField(fieldName);
        field.set(this, fieldValue);
      }
    } catch(Exception error){
      System.err.println("Error in Address.java while setting address ");
    }
  }
}