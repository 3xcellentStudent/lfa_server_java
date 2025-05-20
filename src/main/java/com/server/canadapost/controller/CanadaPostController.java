package com.server.canadapost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.canadapost.services.CanadaPostService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/delivery/canadapost")
public class CanadaPostController {
  
  @Autowired
  private CanadaPostService canadaPostService;

  @GetMapping("pickup-availability/{code}")
  public ResponseEntity<Object> pickupAvailability(@PathVariable String code){
    Object response = canadaPostService.pickupAvailability(code);

    return ResponseEntity.ok(response);
  }

  @PostMapping("ship-price")
  public ResponseEntity<Object> shipPrice(@RequestBody String body){
    Object response = canadaPostService.shipPrice(body);

    return ResponseEntity.ok(response);
  }
}
