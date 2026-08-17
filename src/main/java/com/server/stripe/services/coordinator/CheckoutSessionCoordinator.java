package com.server.stripe.services.coordinator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.common.dto.stripe.orders.OrdersFindOneAndModifyDto;
import com.server.common.types.stripe.orders.OrdersProcessingType;
import com.server.common.types.stripe.orders.OrdersStatusesType;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.services.orders.OrdersService;
import com.server.databases.mongodb.services.product.variation.ProductVariationService;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.expired.StripeCheckoutExpiredDto;
import com.server.stripe.dto.webhook.checkout.events.completed.object.StripeCheckoutCompletedDto;
import com.server.stripe.services.checkout.create.StripeCreateCheckoutSessionService;
import com.server.stripe.services.checkout.expired.StripeExpireCheckoutSessionService;
import com.server.stripe.services.coordinator.validator.MongoDbCartValidator;

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
  private StripeExpireCheckoutSessionService expireCheckoutService;
  @Autowired
  private ProductVariationService productVariationService;

  @Transactional
  public ResponseEntity<Object> createSession(List<CheckoutCreateSessionClientRequestDto> cart){
    List<ProductVariationModel> validatedArray = cartValidatorService.validator(cart);

    ResponseEntity<StripeCheckoutCompletedDto> stripeResponse = createSessionService.create(cart, validatedArray);

    if(!stripeResponse.getStatusCode().is2xxSuccessful()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while sending request to Stripe !");
    }
    
    productVariationService.bulkOpsInventoryUpdate(cart);
    
    StripeCheckoutCompletedDto sessionDto = stripeResponse.getBody();
    
    MainOrderModel orderDataDto = new MainOrderModel();
    orderDataDto.setCheckoutId(sessionDto.id());
    orderDataDto.setInvoiceId(sessionDto.invoice());
    orderDataDto.setStatus(OrdersStatusesType.valueOf(sessionDto.status().toUpperCase()).name());
    orderDataDto.setProcessingStatus(OrdersProcessingType.CREATED.name());
    orderDataDto.setProductList(cart);
    orderDataDto.setCreated(sessionDto.created());
    orderDataDto.setExpiresAt(sessionDto.expiresAt());
      
    ordersService.create(orderDataDto);

    Map<String, String> data = new HashMap<>();
    data.put("clientSecret", sessionDto.clientSecret());
    
    return ResponseEntity.ok(data);
  }

  public ResponseEntity<Object> updateExpiredSession(StripeCheckoutExpiredDto object){
    ResponseEntity<StripeCheckoutExpiredDto> stripeResponse = expireSessionService.getOne(object.id());

    if(!stripeResponse.getStatusCode().is2xxSuccessful()){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while processing Stripe retrieve expired session !");
    }

    OrdersFindOneAndModifyDto dto = new OrdersFindOneAndModifyDto(
      object.id(), null, OrdersStatusesType.valueOf(object.status().toUpperCase()).name(), OrdersProcessingType.CANCELLED.name()
    );

    return ordersService.updateOneById(dto, OrdersStatusesType.valueOf(object.status().toUpperCase()).name());
  }

  @Scheduled(fixedRate = 1800000)
  public void scheduledUpdate(){
    List<MainOrderModel> matchedOrders = ordersService.getAllBySelector("status", List.of(OrdersStatusesType.OPEN.name()));
    System.out.println("ORDERS:" + matchedOrders.size());

    if(matchedOrders.size() == 0){
      return;
    }

    List<StripeCheckoutExpiredDto> sessionsList = expireCheckoutService
    .getMulti(matchedOrders.stream().map(entity -> entity.getCheckoutId()).toList());

    ordersService.bulkUpdate(sessionsList);
  }
}
