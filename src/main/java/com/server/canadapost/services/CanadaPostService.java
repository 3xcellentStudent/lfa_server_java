package com.server.canadapost.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.canadapost.helpers.CanadaPostRoutes;
import com.server.canadapost.helpers.TemplateReader;
import com.server.canadapost.models.ShipPriceRequest;

@Service
public class CanadaPostService {

  private Logger logger = LoggerFactory.getLogger(CanadaPostService.class);

  @Autowired
  private Environment env;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private TemplateReader templateReader;

  public Object pickupAvailability(String postalCode){
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(CanadaPostRoutes.pickupAvailability + postalCode))
      .GET()
      .header("Accept", "application/vnd.cpc.pickup+xml")
      .header("Authorization", "Basic " + env.getProperty("delivery.canadapost.token"))
      .build();

      Object response = HttpClient.newHttpClient()
      .sendAsync(request, null).join().body();

      return response;
    } catch (URISyntaxException error) {
      String message = "Error occured because string could not be parsed as a URI reference";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }

  public Object shipPrice(String requestBodyString){
    try {
      ShipPriceRequest requestBody = objectMapper.readValue(requestBodyString, ShipPriceRequest.class);

      String formattedString = templateReader.getTemplateAsString("/shipPrice", requestBody);

      System.out.println(formattedString);

      // String bodyString = "<?xml version='1.0' encoding='utf-8'?><mailing-scenario xmlns='http://www.canadapost.ca/ws/ship/rate-v4'><customer-number>%s</customer-number><parcel-characteristics><weight>%d</weight><dimensions><length>%d</length><width>%d</width><height>%d</height></dimensions></parcel-characteristics><origin-postal-code>%S</origin-postal-code><destination><domestic><postal-code>%S</postal-code></domestic></destination></mailing-scenario>";

      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(CanadaPostRoutes.shipPrice))
      .POST(BodyPublishers.ofString(formattedString))
      .header("Accept", "application/vnd.cpc.ship.rate-v4+xml")
      .header("Content-Type", "application/vnd.cpc.ship.rate-v4+xml")
      .header("Accept-language", "en-CA or fr-CA")
      .header("Authorization", "Basic " + env.getProperty("delivery.canadapost.token"))
      .build();

      Object response = HttpClient.newHttpClient()
      .sendAsync(request, null).join().body();

      return response;
    } catch (URISyntaxException error) {
      String message = "Error occured because string could not be parsed as a URI reference !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    } catch (JsonProcessingException error){
      String message = "Error while parsing JSON !";
      logger.error(message, error);
      return ResponseEntity.internalServerError().body(message);
    }
  }

}
