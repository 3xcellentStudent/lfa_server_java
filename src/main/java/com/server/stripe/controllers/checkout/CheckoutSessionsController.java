package com.server.stripe.controllers.checkout;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import com.server.stripe.dto.webhook.completed.event.StripeCheckoutWebhookEventDto;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;
import com.server.stripe.models.orders.CheckoutSessionCreateMongodbOrderDto;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;
import com.server.stripe.services.checkout.create.coordinator.CreateCheckoutSessionCoordinator;
import com.server.stripe.types.OrderStatusTypes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stripe/checkout/sessions")
@CrossOrigin("*")
public class CheckoutSessionsController {

  private Logger logger = LoggerFactory.getLogger(CheckoutSessionsController.class);

  // @Autowired
  // private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private CreateCheckoutSessionCoordinator checkoutSessionCoordinator;
  @Autowired
  private OrdersService ordersService;

  @PostMapping("/create")
  public ResponseEntity<Object> createCheckout(@Valid @RequestBody List<CheckoutCreateSessionClientRequestDto> body){
    return checkoutSessionCoordinator.createSession(body);
    // try {
    //   ResponseEntity<Object> response = createSessionService.create(body);

    //   if(response.getStatusCode().equals(HttpStatus.OK)){
    //     CheckoutSessionObjectModel checkoutSessionModel = objectMapper.readValue(response.getBody().toString(), CheckoutSessionObjectModel.class);

    //     // CheckoutSessionCreateMongodbOrderDto orderDataDto = new CheckoutSessionCreateMongodbOrderDto(checkoutSessionModel); - For REST api, (not now)
    //     MainOrderModel orderDataDto = new MainOrderModel(checkoutSessionModel);
        
    //     // Create new order in db
    //     ResponseEntity<Object> createdMongodbOrderEntity = ordersService.create(orderDataDto);
        
    //     if(createdMongodbOrderEntity.getStatusCode().equals(HttpStatus.OK)){
    //       Map<String, String> data = new HashMap<>();
    //       data.put("clientSecret", checkoutSessionModel.getClientSecret());
  
    //       return ResponseEntity.ok(data);
    //     } else {
    //       String message = "Error occured creating order entity in database !";
    //       return ResponseEntity.badRequest().body(message);
    //     }
    //   } else {
    //     String message = "Error occured while checkout processing !";
    //     return ResponseEntity.badRequest().body(message);
    //   }

    // } catch(JsonProcessingException error){
    //   String message = "Error occured while mapping of checkout.session response object !";
    //   return ResponseEntity.badRequest().body(message);
    // }

  }

  @PostMapping("/webhook/completed")
  public ResponseEntity<Object> getWebhook(@Valid @RequestBody StripeCheckoutWebhookEventDto data){
  // public ResponseEntity<Object> completed(@Valid @RequestBody String model){
    // try {
      // StripeCheckoutWebhookEventCompletedDto body = objectMapper.readValue(model, StripeCheckoutWebhookEventCompletedDto.class);

    OrdersFindOneAndModifyDto updateDto = new OrdersFindOneAndModifyDto(data.getInvoiceObject()); // -> Change OrdersFindOneAndModifyDto to local same class for REST API

    ResponseEntity<Object> response = ordersService.updateOneById(updateDto, OrderStatusTypes.OPEN.name());
    return response;
    // } catch (Exception e) {
      // return ResponseEntity.badRequest().build();
    // }
  }

  @PostMapping("/webhook/expired")
  // public ResponseEntity<Object> getWebhook(@Valid @RequestBody StripeCheckoutWebhookEventCompletedDto body){
  public ResponseEntity<Object> expired(@Valid @RequestBody StripeCheckoutWebhookEventDto data){
    System.out.println("EXPIRED===================================================");
    // System.out.println(data);
    OrdersFindOneAndModifyDto updateDto = new OrdersFindOneAndModifyDto(data.getInvoiceObject());

    ResponseEntity<Object> response = ordersService.updateOneById(updateDto, OrderStatusTypes.OPEN.name());
    return response;
  }

}
