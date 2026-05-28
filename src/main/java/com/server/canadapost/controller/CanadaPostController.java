package com.server.canadapost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.canadapost.dto.CreateNonContractShipmentDto;
import com.server.canadapost.dto.CreatePickupRequestDto;
import com.server.canadapost.dto.FindPostOfficeDto;
import com.server.canadapost.dto.PickupRequestPriceDto;
import com.server.canadapost.dto.ShipPriceDto;
import com.server.canadapost.services.CanadaPostService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/delivery/canadapost")
@Validated
public class CanadaPostController {
  
  // @Autowired
  // private CanadaPostService canadaPostService;

  @GetMapping("/pickupavailability")
  public ResponseEntity<Object> pickupAvailability(@RequestParam(required = true) @NotBlank String postalCode){
    // ResponseEntity<Object> response = canadaPostService.pickupAvailability(postalCode);

    // return response;
    return null;
  }

  @PostMapping("/ship/price")
  public ResponseEntity<Object> shipPrice(@RequestBody ShipPriceDto body){
    // ResponseEntity<Object> response = canadaPostService.shipPrice(body);

    // return response;
    return null;
  }

  @PostMapping("/create-ncshipment")
  public ResponseEntity<Object> createShipment(@RequestBody CreateNonContractShipmentDto body){
    // ResponseEntity<Object> response = canadaPostService.createNCShipment(body);

    // return response;
    return null;
  }

  @PostMapping("/pickuprequest/price")
  public ResponseEntity<Object> pickupRequestPrice(@RequestBody PickupRequestPriceDto body){
    // ResponseEntity<Object> response = canadaPostService.pickupRequestPrice(body);

    // return response;
    return null;
  }

  @PostMapping("/create-pickuprequest")
  public ResponseEntity<Object> createPickupRequest(@RequestBody CreatePickupRequestDto body){
    // ResponseEntity<Object> response = canadaPostService.createPickupRequest(body);

    // return response;
    return null;
  }

  @GetMapping("/postoffice/find")
  public ResponseEntity<Object> findPostOffice(@Valid @ModelAttribute FindPostOfficeDto body){
    // ResponseEntity<Object> response = canadaPostService.findPostOffice(body);

    // return response;
    return null;
  }

  @GetMapping("/postoffice/details")
  public ResponseEntity<Object> findPostOfficeDetails(@RequestParam(required = true) @NotBlank String detailsUri){
    // ResponseEntity<Object> response = canadaPostService.findPostOfficeDetails(detailsUri);

    // return response;
    return null;
  }

}