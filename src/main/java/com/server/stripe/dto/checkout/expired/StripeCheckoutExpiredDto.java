package com.server.stripe.dto.checkout.expired;

import java.time.Instant;

import org.springframework.data.annotation.Id;

public record StripeCheckoutExpiredDto(@Id String id, Long created, Instant expiresAt, String status){}