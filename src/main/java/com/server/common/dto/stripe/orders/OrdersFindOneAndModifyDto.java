package com.server.common.dto.stripe.orders;

public record OrdersFindOneAndModifyDto(String checkoutId, String invoiceId, String status, String processingStatus){}
