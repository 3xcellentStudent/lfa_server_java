package com.server.databases.mongodb.controllers.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.server.common.dto.stripe.orders.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.models.orders.MainOrderModel;
import com.server.databases.mongodb.models.orders.types.OrderStatusTypes;
import com.server.databases.mongodb.services.orders.OrdersService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mongodb/orders")
@Validated
public class OrderController {

  @Autowired
  private OrdersService ordersService;
  
  @GetMapping("/get/id")
  public ResponseEntity<Object> getById(@RequestParam @NotBlank String id){
    MainOrderModel foundDoc = ordersService.getOneById(id);

     if(foundDoc == null){
      String message = "Document with ID: " + id + " was not found !";
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
    
    return ResponseEntity.ok(foundDoc);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getAll(){
    return ResponseEntity.ok(ordersService.getAll());
  }

  @GetMapping("/get/by/selector")
  public ResponseEntity<Object> getAllBySelector(@RequestParam @NotBlank String selector, @RequestParam @NotEmpty List<String> status){
    return ResponseEntity.ok(ordersService.getAllBySelector(selector, status));
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> findOneAndUpdate(@Valid @RequestBody OrdersFindOneAndModifyDto body){
    return ordersService.updateOneById(body, OrderStatusTypes.values().toString());
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> findAllByIdAndRemove(@RequestBody @NotEmpty List<String> id){
    return ordersService.findAllByIdAndRemove(id);
  }

}
