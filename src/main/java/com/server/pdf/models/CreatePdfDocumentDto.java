package com.server.pdf.models;

import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel;
import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;

public class CreatePdfDocumentDto {
  public String invoiceId;
  public Address address;
  public String billingAddress;
  public Integer amountTotal;
  public Long created;
  public String currency;
  public String paymentStatus;
  public String payerName;

  public CreatePdfDocumentDto(){}

  public CreatePdfDocumentDto(StripeCheckoutSessionsDataModel dataModel){
    this.invoiceId = dataModel.getId();
    this.address = dataModel.collectedInformation.shippingDetails.address;
    this.billingAddress = dataModel.billingAddressCollection;
    this.amountTotal = dataModel.amountTotal;
    this.created = dataModel.created;
    this.currency = dataModel.currency;
    this.paymentStatus = dataModel.paymentStatus;
    this.payerName = dataModel.collectedInformation.shippingDetails.name;
  }

}