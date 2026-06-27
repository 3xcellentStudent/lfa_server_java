package com.server.stripe.services.checkout.create.coordinator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.services.orders.OrdersService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.webhook.completed.object.CheckoutSessionObjectModel;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;

@Service
@Transactional
public class CreateCheckoutSessionCoordinator {

  @Autowired
  private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private OrdersService ordersService;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createSession(List<CheckoutCreateSessionClientRequestDto> body){
    try {
      ResponseEntity<Object> response = createSessionService.create(body);
  
      CheckoutSessionObjectModel checkoutSessionModel = objectMapper.readValue(response.getBody().toString(), CheckoutSessionObjectModel.class);
  
      MainOrderModel orderDataDto = new MainOrderModel(checkoutSessionModel);
      
      // Create new order in db
      ordersService.create(orderDataDto);

      Map<String, String> data = new HashMap<>();
      data.put("clientSecret", checkoutSessionModel.getClientSecret());
      
      return ResponseEntity.ok(data);
    } catch(JsonProcessingException error){
      return ResponseEntity.badRequest().build();
    }
  }
}
