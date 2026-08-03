package com.server.databases.mongodb.models.orders;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class MainOrderModel{
  private @Id String checkoutId;
  private String invoiceId;
  private String status;
  private String processingStatus;
  private List<CheckoutCreateSessionClientRequestDto> productList;
  private @CreatedDate Instant expiresAt;
  private @LastModifiedDate Instant created;
}
