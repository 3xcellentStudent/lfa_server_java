package com.server.services.pdf.models;

import org.json.JSONObject;
// import org.springframework.stereotype.Service;

// @Service
public class CaptureResponseObject {
  public String orderId;
  public String status;
  public Address address;
  public boolean final_capture;
  public String gross_amount;
  public String currency_code;
  public String create_time;
  public Payer payer;

  public CaptureResponseObject(JSONObject body){
    JSONObject firstPurchaseUnitObject = firstPurchaseUnit(body);
    JSONObject firstCaptureObject = firstCaptureObject(firstPurchaseUnitObject);
    JSONObject firstSellerReceivableBreakdownObject = sellerReceivableBreakdown(firstCaptureObject);

    this.orderId = body.getString("id");
    this.status = body.getString("status");
    this.address = new Address(firstPurchaseUnitObject);
    this.final_capture = firstCaptureObject.getBoolean("final_capture");
    this.gross_amount = firstSellerReceivableBreakdownObject.getString("value");
    this.currency_code = firstSellerReceivableBreakdownObject.getString("currency_code");
    this.create_time = firstCaptureObject.getString("create_time");
    this.payer = new Payer(body);
  }

  private JSONObject firstPurchaseUnit(JSONObject body){
    return body.getJSONArray("purchase_units").getJSONObject(0);
  }

  private JSONObject firstCaptureObject(JSONObject purchaseUnit){
    return purchaseUnit.getJSONObject("payments").getJSONArray("captures")
    .getJSONObject(0);
  }

  private JSONObject sellerReceivableBreakdown(JSONObject captureObject){
    return captureObject.getJSONObject("seller_receivable_breakdown").getJSONObject("gross_amount");
  }
}