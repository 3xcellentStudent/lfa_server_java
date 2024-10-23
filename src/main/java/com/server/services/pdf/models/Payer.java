package com.server.services.pdf.models;

import org.json.JSONObject;

public class Payer {
  public String first_name;
  public String second_name;
  public String email_address;

  public Payer(JSONObject body){
    JSONObject payer = body.getJSONObject("payer");
    JSONObject name = payer.getJSONObject("name");

    this.first_name = name.getString("given_name");
    this.second_name = name.getString("surname");
    this.email_address = payer.getString("email_address");
  }
}