package com.server.stripe.controllers.checkout;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.dto.orders.request.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.services.orders.OrdersService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.webhook.completed.event.StripeCheckoutWebhookEventCompletedDto;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;
import com.server.stripe.models.mongodb.orders.CheckoutSessionCreateMongodbOrderDto;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;
import com.server.stripe.services.checkout.webhooks.completed.StripeCheckoutCompletedWebhookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  private Logger logger = LoggerFactory.getLogger(CheckoutSessionsController.class);

  @Autowired
  private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private StripeCheckoutCompletedWebhookService completedWebhookService;
  @Autowired
  private OrdersService ordersService;
  @Autowired
  private ObjectMapper objectMapper;

  @PostMapping("/create")
  public ResponseEntity<Object> createCheckout(@Valid @RequestBody List<CheckoutCreateSessionClientRequestDto> body){
    try {
      ResponseEntity<Object> response = createSessionService.create(body);

      if(response.getStatusCode().equals(HttpStatus.OK)){
        CheckoutSessionObjectModel checkoutSessionModel = objectMapper.readValue(response.getBody().toString(), CheckoutSessionObjectModel.class);

        // CheckoutSessionCreateMongodbOrderDto orderDataDto = new CheckoutSessionCreateMongodbOrderDto(checkoutSessionModel); - For REST api, (not now)
        MainOrderModel orderDataDto = new MainOrderModel(checkoutSessionModel);
        
        // Create new order in db
        ResponseEntity<Object> createdMongodbOrderEntity = ordersService.create(orderDataDto);
        
        if(createdMongodbOrderEntity.getStatusCode().equals(HttpStatus.OK)){
          Map<String, String> data = new HashMap<>();
          data.put("clientSecret", checkoutSessionModel.getClientSecret());
  
          return ResponseEntity.ok(data);
        } else {
          return createdMongodbOrderEntity;
        }
      } else {
        String message = "Error occured while checkout processing !";
        return ResponseEntity.badRequest().body(message);
      }

    } catch(JsonProcessingException error){
      String message = "Error occured while mapping of checkout.session response object !";
      return ResponseEntity.badRequest().body(message);
    }

  }

  @PostMapping("/webhook/completed")
  // public ResponseEntity<Object> getWebhook(@Valid @RequestBody StripeCheckoutWebhookEventCompletedDto body){
  public ResponseEntity<Object> getWebhook(@Valid @RequestBody String model){
    try {
      System.out.println(model);
      StripeCheckoutWebhookEventCompletedDto body = objectMapper.readValue(model, StripeCheckoutWebhookEventCompletedDto.class);

    OrdersFindOneAndModifyDto updateDto = new OrdersFindOneAndModifyDto(body.getInvoiceObject()); // -> Change OrdersFindOneAndModifyDto to local same class for REST API

    ResponseEntity<Object> response = ordersService.updateOneById(updateDto);
    return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    // System.out.println("Invoice ID: " + body.getInvoiceObject().getId());

    // OrdersFindOneAndModifyDto updateDto = new OrdersFindOneAndModifyDto(body.getInvoiceObject()); // -> Change OrdersFindOneAndModifyDto to local same class for REST API

    // ResponseEntity<Object> response = ordersService.updateOneById(updateDto);

    // return ResponseEntity.badRequest().build();
    // if(response.getStatusCode().equals(HttpStatus.OK)){
    //   String message = "Order ID: " + body.getInvoiceObject().getId() + " successfuly updated !";
    //   logger.info(message);
    //   return response;
    // } else {
    //   String message = "Order ID: " + body.getInvoiceObject().getId() + " failed to update !";
    //   logger.info(message);
    //   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    // }
  }

}
