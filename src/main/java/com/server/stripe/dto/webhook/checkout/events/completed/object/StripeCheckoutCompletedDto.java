package com.server.stripe.dto.webhook.checkout.events.completed.object;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StripeCheckoutCompletedDto(@Id String id, String clientSecret, Instant created, Instant expiresAt, String invoice, String status) {}