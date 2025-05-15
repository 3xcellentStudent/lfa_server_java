package com.server.mailer.dto;

import org.springframework.stereotype.Component;

import com.common.models.stripe.invoices.submodels.StripeCheckoutSessionsDataModel.CollectedInformation.ShippingDetails.Address;


@Component
public class ReceivedPdfAndEmailDataDto {

  public String fileName;
  public byte[] fileBytes;
  public EmailContent emailContent;

  public class EmailContent {
    public String invoiceId;
    public Address address;
    public String billingAddress;
    public Integer amountTotal;
    public Long created;
    public String currency;
    public String paymentStatus;
    public String customerName;
    public String customerEmail;
  }

  public ReceivedPdfAndEmailDataDto(){}

  public ReceivedPdfAndEmailDataDto(ReceivedPdfAndEmailDataDto dto){
    this.fileName = dto.fileName;
    this.fileBytes = dto.fileBytes;
    this.emailContent = dto.emailContent; 
  }

  public String getFileName(){
    return this.fileName;
  }
  public byte[] getFileBytes(){
    return this.fileBytes;
  }

  public EmailContent getEmailContent(){
    return this.emailContent;
  }

}
