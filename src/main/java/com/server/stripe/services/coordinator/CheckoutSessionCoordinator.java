package com.server.stripe.services.coordinator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.orders.OrdersService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;
import com.server.stripe.dto.webhook.checkout.events.completed.object.StripeCheckoutCompletedDto;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;
import com.server.stripe.services.checkout.expired.StripeExpireCheckoutSessionService;
import com.server.stripe.services.coordinator.helper.MongoDbCartValidator;

@Service
public class CheckoutSessionCoordinator {
  
  @Autowired
  private StripeCreateCheckoutSessionService createSessionService;
  @Autowired
  private StripeExpireCheckoutSessionService expireSessionService;
  @Autowired
  private MongoDbCartValidator cartValidatorService;
  @Autowired
  private OrdersService ordersService;
  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  public ResponseEntity<Object> createSession(List<CheckoutCreateSessionClientRequestDto> cart){
    List<ProductVariationModel> validatedArray = cartValidatorService.validator(cart);

    try {
      ResponseEntity<Object> response = createSessionService.create(cart, validatedArray);
  
      StripeCheckoutCompletedDto checkoutSessionModel = objectMapper.readValue(response.getBody().toString(), StripeCheckoutCompletedDto.class);
  
      MainOrderModel orderDataDto = new MainOrderModel(checkoutSessionModel, cart);
      
      // Create new order in db
      ordersService.create(orderDataDto);
  
      Map<String, String> data = new HashMap<>();
      data.put("clientSecret", checkoutSessionModel.clientSecret());
      
      return ResponseEntity.ok(data);
    } catch(JsonProcessingException ex){
      throw new RuntimeException("Stripe JSON parsing failed !", ex);
    }
  }

  public ResponseEntity<Object> updateExpiredSession(StripeCheckoutExpiredDto object){
    // MainOrderModel foundDoc = ordersService.getOneById(object.id());
    
    // if(foundDoc == null){
    //   String message = "Document with ID: " + object.id() + " was not found !";
    //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    // }
    
    ResponseEntity<Object> stripeResponse = expireSessionService.sendRequestSync(object.id());

    if(!stripeResponse.getStatusCode().equals(HttpStatus.OK)){
      return stripeResponse;
    }

    
    
    return ResponseEntity.ok(null);
  }
}
