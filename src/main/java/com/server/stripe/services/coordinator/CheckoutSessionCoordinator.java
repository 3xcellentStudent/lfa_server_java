package com.server.stripe.services.coordinator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.server.stripe.services.checkout.expired.StripeExpireCheckoutSessionService;

@Service
@Transactional
public class CheckoutSessionCoordinator {

  private final String clientSecretKey = "clientSecret";

  @Autowired
  private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private StripeExpireCheckoutSessionService expireSessionService;
  @Autowired
  private OrdersService ordersService;
  @Autowired
  private ObjectMapper objectMapper;

  public ResponseEntity<Object> createSession(List<CheckoutCreateSessionClientRequestDto> body){
    try {
      ResponseEntity<Object> response = createSessionService.create(body);
  
      CheckoutSessionObjectModel checkoutSessionModel = objectMapper.readValue(response.getBody().toString(), CheckoutSessionObjectModel.class);
  
      MainOrderModel orderDataDto = new MainOrderModel(checkoutSessionModel, body);
      
      // Create new order in db
      ordersService.create(orderDataDto);

      Map<String, String> data = new HashMap<>();
      data.put(clientSecretKey, checkoutSessionModel.clientSecret());
      
      return ResponseEntity.ok(data);
    } catch(JsonProcessingException error){
      return ResponseEntity.badRequest().build();
    }
  }

  public ResponseEntity<Object> updateExpiredSession(String invoiceId){
    expireSessionService.sendRequest(invoiceId);

    return ResponseEntity.ok(null);
  }
}
