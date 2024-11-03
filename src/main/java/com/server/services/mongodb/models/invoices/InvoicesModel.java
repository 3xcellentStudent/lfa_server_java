package com.server.services.mongodb.models.invoices;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.services.pdf.models.CaptureResponseObject;


@Document
public class InvoicesModel {
  
  @Id
  public String id;
  public String email;
  public String orderId;
  public String status;
  // public Address address;
  public boolean final_capture;
  public String gross_amount;
  public String currency_code;
  public String create_time;
  public String capture_time;
  // public Payer payer;

  public InvoicesModel(CaptureResponseObject object){
    // this.id = 
    // this.email = 
    this.orderId = object.orderId;
    this.status = object.status;
    this.final_capture = object.final_capture;
    this.gross_amount = object.gross_amount;
    this.currency_code = object.currency_code;
    this.create_time = object.create_time;
    // this.capture_time = object
  }
}
