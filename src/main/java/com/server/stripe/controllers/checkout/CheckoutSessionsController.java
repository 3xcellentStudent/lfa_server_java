package com.server.stripe.controllers.checkout;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.orders.request.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.services.orders.OrdersService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.webhook.checkout.events.completed.StripeCheckoutEventDto;
import com.server.stripe.dto.webhook.checkout.events.completed.object.StripeCheckoutCompletedDto;
import com.server.stripe.dto.webhook.checkout.events.expired.StripeCheckoutExpiredEvent;
import com.server.stripe.services.coordinator.CheckoutSessionCoordinator;
import com.server.stripe.types.OrderStatusTypes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  @Autowired
  private CheckoutSessionCoordinator checkoutSessionCoordinator;
  @Autowired
  private OrdersService ordersService;

  @PostMapping("/create")
  public ResponseEntity<Object> createCheckout(@Valid @RequestBody List<CheckoutCreateSessionClientRequestDto> body){
    return checkoutSessionCoordinator.createSession(body);
  }

  @PostMapping("/webhook/completed")
  public ResponseEntity<Object> getWebhook(@Valid @RequestBody StripeCheckoutEventDto body){
    StripeCheckoutCompletedDto sessionModel = body.data().object();
    OrdersFindOneAndModifyDto updateDto = new OrdersFindOneAndModifyDto(sessionModel.id(), sessionModel.invoice(), sessionModel.status());

    ResponseEntity<Object> response = ordersService.updateOneById(updateDto, OrderStatusTypes.OPEN.name());
    return response;
  }

  @PostMapping("/webhook/expired")
  public ResponseEntity<Object> expired(@Valid @RequestBody StripeCheckoutExpiredEvent body){
    System.out.println("EXPIRED===================================================");
    System.out.println(body.data().object().id());
    System.out.println(body.data().object().status());

    checkoutSessionCoordinator.updateExpiredSession(body.data().object());

    // ResponseEntity<Object> response = ordersService.updateOneById(updateDto, OrderStatusTypes.EXPIRED.name());
    return ResponseEntity.ok(null);
  }

}
