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
  
  private HttpClient httpClient = HttpClient.newHttpClient();

  public Object pickupAvailability(String postalCode){
    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(CanadaPostRoutes.pickupAvailability + postalCode))
      .GET()
      .header("Accept", "application/vnd.cpc.pickup+xml")
      .header("Authorization", "Basic " + env.getProperty("delivery.canadapost.token"))
      .build();

      Object response = httpClient.sendAsync(request, null).join().body();

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

      Object response = httpClient.sendAsync(request, null).join().body();

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

  public ResponseEntity<Object> createShipment(){
    // curl https://ct.soa-gw.canadapost.ca/rs/0006142578/ncshipment -X POST -H "Accept":"application/vnd.cpc.ncshipment-v4+xml" -H "Content-Type":"application/vnd.cpc.ncshipment-v4+xml" -H "Authorization":"Basic Yjc1YjYxY2ZhMmI0NGIzNToxYTc2NDU5M2Y5MjhjNDY3YjI4MDQx" -H "Accept-language":"en-CA or fr-CA" -d "<?xml version='1.0' encoding='utf-8'?><non-contract-shipment xmlns='http://www.canadapost.ca/ws/ncshipment-v4'><requested-shipping-point>T6C4G0</requested-shipping-point><delivery-spec><service-code>DOM.EP</service-code><sender><company>Andrew Corporation</company><contact-phone>7807295953</contact-phone><address-details><address-line-1>9009 85 St NW</address-line-1><city>Edmonton</city><prov-state>AB</prov-state><postal-zip-code>T6C3C6</postal-zip-code></address-details></sender><destination><name>John Doe</name><company>Consumer</company><address-details><address-line-1>701 32nd St W</address-line-1><city>Saskatoon</city><prov-state>SK</prov-state><country-code>CA</country-code><postal-zip-code>S7L2A1</postal-zip-code></address-details></destination><options><option><option-code>DC</option-code></option></options><parcel-characteristics><weight>3</weight><dimensions><length>6</length><width>4</width><height>2</height></dimensions></parcel-characteristics><preferences><show-packing-instructions>true</show-packing-instructions></preferences></delivery-spec></non-contract-shipment>"

    try {
      HttpRequest request = HttpRequest.newBuilder()
      .uri(new URI(CanadaPostRoutes.createShipment))
      .POST(BodyPublishers.ofString("<?xml version='1.0' encoding='utf-8'?><non-contract-shipment xmlns='http://www.canadapost.ca/ws/ncshipment-v4'><requested-shipping-point>T6C4G0</requested-shipping-point><delivery-spec><service-code>DOM.EP</service-code><sender><company>Andrew Corporation</company><contact-phone>7807295953</contact-phone><address-details><address-line-1>9009 85 St NW</address-line-1><city>Edmonton</city><prov-state>AB</prov-state><postal-zip-code>T6C3C6</postal-zip-code></address-details></sender><destination><name>John Doe</name><company>Consumer</company><address-details><address-line-1>701 32nd St W</address-line-1><city>Saskatoon</city><prov-state>SK</prov-state><country-code>CA</country-code><postal-zip-code>S7L2A1</postal-zip-code></address-details></destination><options><option><option-code>DC</option-code></option></options><parcel-characteristics><weight>3</weight><dimensions><length>6</length><width>4</width><height>2</height></dimensions></parcel-characteristics><preferences><show-packing-instructions>true</show-packing-instructions></preferences></delivery-spec></non-contract-shipment>"))
      .header("Accept", "application/vnd.cpc.ncshipment-v4+xml")
      .header("Content-Type", "application/vnd.cpc.ncshipment-v4+xml")
      .header("Authorization", "Basic " + env.getProperty("delivery.canadapost.token"))
      .header("Accept-language", "en-CA or fr-CA")
      .build();

      Object response = httpClient.sendAsync(request, null).join().body();

      return ResponseEntity.ok(response);
    } catch(URISyntaxException error){
      String message = "Error occured because string could not be parsed as a URI reference !";
      logger.error(message, error);
      return ResponseEntity.badRequest().body(message);
    }
  }

}
