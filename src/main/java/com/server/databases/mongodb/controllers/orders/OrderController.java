package com.server.databases.mongodb.controllers.orders;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.common.api.exceptions.mongo.ResourceNotFoundException;
import com.server.common.dto.stripe.orders.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.models.orders.OrderModel;
import com.server.databases.mongodb.services.MongoDbMainService;
import com.server.databases.mongodb.services.orders.OrdersService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mongodb/orders")
@Validated
public class OrderController {

  private static final Set<String> PAGINATION_FIELDS = Set.of(
    "checkoutId", 
    "status", 
    "createdAt"
  );

  Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private OrdersService ordersService;
  @Autowired
  private MongoDbMainService mainService;

  @Value("${databases.mongodb.collections.orders}")
  private String ordersCollection;

  @GetMapping("/get")
  public ResponseEntity<Object> getByPage(
    @RequestParam(defaultValue = "0") @Min(0) int page, 
    @RequestParam(defaultValue = "20") @Min(1) int size, 
    @RequestParam(defaultValue = "status") String selector
  ){
    if(!PAGINATION_FIELDS.contains(selector)){
      String message = "\"Selector\" must be same as: " + String.join(", ", PAGINATION_FIELDS);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    int validatedSize = Math.min(size, 50);

    Page<OrderModel> orders = mainService.findByPage(page, validatedSize, selector, OrderModel.class, ordersCollection);

    return ResponseEntity.ok(orders);
  }

  @GetMapping("/get/id")
  public ResponseEntity<Object> getById(@RequestParam @NotBlank String id){
    OrderModel foundDoc = ordersService.getOneById(id);

    if(foundDoc == null){
      String message = "Document with ID: " + id + " was not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    
    return ResponseEntity.ok(foundDoc);
  }

  @GetMapping("/get/by/selector")
  public ResponseEntity<Object> getAllBySelector(@RequestParam @NotBlank String selector, @RequestParam @NotEmpty List<String> status){
    return ResponseEntity.ok(ordersService.getOpenOrders(selector, status));
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> findOneAndUpdate(@Valid @RequestBody OrdersFindOneAndModifyDto body){
    OrderModel updatedDoc = ordersService.updateOneById(body, body.status());

    if(updatedDoc != null){
      logger.info("Document ID \"" + body.checkoutId() + "\" was successfully updated !");
      return ResponseEntity.ok(updatedDoc);
    } else {
      String message = "Document ID: " + body.checkoutId() + " was not found !";
      throw new ResourceNotFoundException(message);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> findAllByIdAndRemove(@RequestBody @NotEmpty List<String> id){
    List<OrderModel> ordersList = ordersService.findAllByIdAndRemove(id);

    return ResponseEntity.ok(ordersList);
  }

}
