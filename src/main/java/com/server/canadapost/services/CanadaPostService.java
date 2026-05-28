package com.server.canadapost.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.server.canadapost.dto.CreateNonContractShipmentDto;
import com.server.canadapost.dto.CreatePickupRequestDto;
import com.server.canadapost.dto.FindPostOfficeDto;
import com.server.canadapost.dto.PickupRequestPriceDto;
import com.server.canadapost.dto.ShipPriceDto;
import com.server.canadapost.dto.xml.ShipPriceXmlDto;
import com.server.canadapost.helpers.TemplateReader;

@Service
public class CanadaPostService {

  private Logger logger = LoggerFactory.getLogger(CanadaPostService.class);

  // @Autowired
  // private TemplateReader templateReader;

  // @Value("${delivery.canadapost.urls.create_ncshipment}")
  // private String CREATE_NCSHIPMENT_ROUTE;
  // @Value("${delivery.canadapost.urls.pickup_availability}")
  // private String PICKUP_AVAILABILITY_ROUTE;
  // @Value("${delivery.canadapost.urls.ship_price}")
  // private String SHIP_PRICE_ROUTE;
  // @Value("${delivery.canadapost.urls.pickuprequest_price}")
  // private String PICKUPREQUEST_PRICE_ROUTE;
  // @Value("${delivery.canadapost.urls.create_pickuprequest}")
  // private String CREATE_PICKUP_REQUEST_ROUTE;
  // @Value("${delivery.canadapost.urls.find_postoffice}")
  // private String FIND_POSTOFFICE_ROUTE;

  // @Value("${delivery.canadapost.templates.path.pickup_request_price}")
  // private String PICKUP_REQUEST_PRICE_TEMPLATE_PATH;
  // @Value("${delivery.canadapost.templates.path.create_return}")
  // private String CREATE_RETURN_TEMPLATE_PATH;
  // @Value("${delivery.canadapost.templates.path.ship_price}")
  // private String SHIP_PRICE_TEMPLATE_PATH;
  // @Value("${delivery.canadapost.templates.path.create_ncshipment}")
  // private String CREATE_NCSHIPMENT_TEMPLATE_PATH;
  // @Value("${delivery.canadapost.templates.path.create_pickuprequest}")
  // private String CREATE_PICKUP_REQUEST_TEMPLATE_PATH;

  // @Value("${delivery.canadapost.token}")
  // private String canadapostToken;
  
  // private HttpClient httpClient = HttpClient.newHttpClient();

  // private XmlMapper xmlMapper = new XmlMapper();

  // public ResponseEntity<Object> pickupAvailability(String postalCode){
  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(PICKUP_AVAILABILITY_ROUTE + "/" + postalCode))
  //     .GET()
  //     .header("Accept", "application/vnd.cpc.pickup+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     Object response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch (URISyntaxException error) {
  //     String message = "Error occured because string could not be parsed as a URI reference";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

  // public ResponseEntity<Object> shipPrice(ShipPriceDto body){
  //   try {
  //     String formattedString = templateReader.getTemplateAsString(SHIP_PRICE_TEMPLATE_PATH, body);

  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(SHIP_PRICE_ROUTE))
  //     .POST(BodyPublishers.ofString(formattedString))
  //     .header("Accept", "application/vnd.cpc.ship.rate-v4+xml")
  //     .header("Content-Type", "application/vnd.cpc.ship.rate-v4+xml")
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .build();

  //     Object response = httpClient.sendAsync(request, BodyHandlers.ofString()).join().body();

  //     ShipPriceXmlDto xmlDto = xmlMapper.readValue(response.toString(), ShipPriceXmlDto.class);

  //     System.out.println(xmlDto.getQuotes().get(0).getPriceDetails().getBase());

  //     return ResponseEntity.ok(response);
  //   } catch (URISyntaxException error) {
  //     String message = "Error occured because string could not be parsed as a URI reference !";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   } catch(JsonProcessingException error){
  //     String message = "Error occured while processing the XML response from Canada Post API services in \"/ship/price\" !";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

  // public ResponseEntity<Object> createNCShipment(CreateNonContractShipmentDto body){

  //   String formattedString = templateReader.getTemplateAsString(CREATE_NCSHIPMENT_TEMPLATE_PATH, body);

  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(CREATE_NCSHIPMENT_ROUTE))
  //     .POST(BodyPublishers.ofString(formattedString))
  //     .header("Accept", "application/vnd.cpc.ncshipment-v4+xml")
  //     .header("Content-Type", "application/vnd.cpc.ncshipment-v4+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     String response = httpClient.sendAsync(request, BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch(URISyntaxException error){
  //     String message = "Error occured because string could not be parsed as a URI reference !";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

  // public ResponseEntity<Object> pickupRequestPrice(PickupRequestPriceDto body){

  //   String formattedString = templateReader.getTemplateAsString(PICKUP_REQUEST_PRICE_TEMPLATE_PATH, body);

  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(PICKUPREQUEST_PRICE_ROUTE))
  //     .POST(BodyPublishers.ofString(formattedString))
  //     .header("Accept", "application/vnd.cpc.pickuprequest+xml")
  //     .header("Content-Type", "application/vnd.cpc.pickuprequest+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     String response = httpClient.sendAsync(request, BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch (URISyntaxException error) {
  //     String message = "Error occured because string could not be parsed as a URI reference !";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }
  
  // public ResponseEntity<Object> createPickupRequest(CreatePickupRequestDto body){

  //   String formattedString = templateReader.getTemplateAsString(CREATE_PICKUP_REQUEST_TEMPLATE_PATH, body);

  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(CREATE_PICKUP_REQUEST_ROUTE))
  //     .POST(BodyPublishers.ofString(formattedString))
  //     .header("Accept", "application/vnd.cpc.pickuprequest+xml")
  //     .header("Content-Type", "application/vnd.cpc.pickuprequest+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     String response = httpClient.sendAsync(request, BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch(URISyntaxException error){
  //     String message = "Error occured because string could not be parsed as a URI reference !";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

  // public ResponseEntity<Object> findPostOffice(FindPostOfficeDto body){

  //   String uriEncoded = String.format(
  //     FIND_POSTOFFICE_ROUTE, 
  //     UriEncoder.encode(body.getPostalCode()), 
  //     UriEncoder.encode(body.getProvince()), 
  //     UriEncoder.encode(body.getCity()), 
  //     UriEncoder.encode(body.getStreetName()), 
  //     UriEncoder.encode(body.getMaximum())
  //   );

  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(uriEncoded))
  //     .GET()
  //     .header("Accept", "application/vnd.cpc.postoffice+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     Object response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch (URISyntaxException error) {
  //     String message = "Error occured because string could not be parsed as a URI reference";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

  // public ResponseEntity<Object> findPostOfficeDetails(String detailsUri){
  //   System.out.println(detailsUri);
  //   try {
  //     HttpRequest request = HttpRequest.newBuilder()
  //     .uri(new URI(detailsUri))
  //     .GET()
  //     .header("Accept", "application/vnd.cpc.postoffice+xml")
  //     .header("Authorization", "Basic " + canadapostToken)
  //     .header("Accept-language", "en-CA or fr-CA")
  //     .build();

  //     Object response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body();

  //     return ResponseEntity.ok(response);
  //   } catch (URISyntaxException error) {
  //     String message = "Error occured because string could not be parsed as a URI reference";
  //     logger.error(message, error);
  //     return ResponseEntity.badRequest().body(message);
  //   }
  // }

}
