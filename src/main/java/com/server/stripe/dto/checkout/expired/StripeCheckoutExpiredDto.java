package com.server.stripe.dto.checkout.expired;

import org.springframework.data.annotation.Id;

public record StripeCheckoutExpiredDto(@Id String id, Long created, Long expiresAt, String status){}