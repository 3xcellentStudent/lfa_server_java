// package com.common.dto.coordinator;

// import com.common.models.stripe.invoices.StripeCheckoutSessionsWrapperModel;
// import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel;
// import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;

// public class AfterCreationStripeInvoiceDto {
//   public String invoiceId;
//   public Address address;
//   public String billingAddress;
//   public Integer amountTotal;
//   public Long created;
//   public String currency;
//   public String paymentStatus;
//   public String customerName;
//   public String customerEmail;

//   public AfterCreationStripeInvoiceDto(){}

//   public AfterCreationStripeInvoiceDto(StripeCheckoutSessionsWrapperModel dataModel){
//     this.invoiceId = dataModel.invoiceId;
    
//     this.address = dataModel.collectedInformation.shippingDetails.address;
//     this.billingAddress = dataModel.billingAddressCollection;
//     this.amountTotal = dataModel.amountTotal;
//     this.created = dataModel.created;
//     this.currency = dataModel.currency;
//     this.paymentStatus = dataModel.paymentStatus;
//     this.customerName = dataModel.collectedInformation.shippingDetails.name;
//     this.customerEmail = dataModel.customerDetails.email;
//   }

// }

package com.common.dto.coordinator;

import com.common.models.stripe.invoices.StripeCheckoutSessionsWrapperModel;
import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel;
import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;

public class AfterCreationStripeInvoiceDto {
  public String invoiceId;
  public Address address;
  public String billingAddress;
  public Integer amountTotal;
  public Long created;
  public String currency;
  public String paymentStatus;
  public String customerName;
  public String customerEmail;

  public AfterCreationStripeInvoiceDto(){}

  public AfterCreationStripeInvoiceDto(StripeCheckoutSessionsWrapperModel dataModel){
    StripeCheckoutSessionsDataModel stripeDataObject = dataModel.stripeObject.data.object;

    this.invoiceId = dataModel.invoiceId;
    this.address = stripeDataObject.collectedInformation.shippingDetails.address;
    this.billingAddress = stripeDataObject.billingAddressCollection;
    this.amountTotal = stripeDataObject.amountTotal;
    this.created = stripeDataObject.created;
    this.currency = stripeDataObject.currency;
    this.paymentStatus = stripeDataObject.paymentStatus;
    this.customerName = stripeDataObject.collectedInformation.shippingDetails.name;
    this.customerEmail = stripeDataObject.customerDetails.email;
  }

}

