package com.server.stripe.controllers.checkout;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.webhook.completed.event.StripeCheckoutWebhookEventCompletedDto;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;
import com.server.stripe.services.checkout.webhooks.completed.StripeCheckoutCompletedWebhookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  @Autowired
  private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private StripeCheckoutCompletedWebhookService completedWebhookService;

  @PostMapping("/create")
  public ResponseEntity<Object> createCheckout(@Valid @RequestBody List<CheckoutCreateSessionClientRequestDto> body){
    return createSessionService.create(body);
  }

  // @PostMapping("/webhook/completed")
  // public void getWebhook(@Valid @RequestBody StripeCheckoutWebhookEventCompletedDto body){
  //   try {
  //     ObjectMapper mapper = new ObjectMapper();
  //     String response = mapper.writeValueAsString(body);
  //     System.out.println("Completed: " + response);
  //     completedWebhookService.updateInvoiceInDatabase(body);
  //   } catch (JsonProcessingException e) {
  //     System.out.println(e);
  //   }
  // }

  @PostMapping("/webhook/completed")
  public void getWebhook(@Valid @RequestBody String body){
    System.out.println("Completed: " + body);
  }

}
