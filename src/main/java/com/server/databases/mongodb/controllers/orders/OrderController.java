package com.server.databases.mongodb.controllers.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.dto.orders.request.OrdersFindOneAndModifyDto;
import com.server.databases.mongodb.dto.orders.request.OrdersGetOneByIdDto;
import com.server.databases.mongodb.services.orders.OrdersService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mongodb/orders")
@Validated
public class OrderController {

  @Autowired
  private OrdersService ordersService;
  
  @GetMapping("/get/id")
  public ResponseEntity<Object> getById(@Valid @RequestBody OrdersGetOneByIdDto body){
    return ordersService.getOneById(body);
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getAll(){
    return ordersService.getAll();
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> findOneAndUpdate(@Valid @RequestBody OrdersFindOneAndModifyDto body){
    return ordersService.updateOneById(body);
  }

}
