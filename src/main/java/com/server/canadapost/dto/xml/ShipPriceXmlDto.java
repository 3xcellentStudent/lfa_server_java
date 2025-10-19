package com.server.canadapost.dto.xml;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "price-quotes")
public class ShipPriceXmlDto {

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "price-quote")
  private List<PriceQuote> quotes;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PriceQuote {

    @JacksonXmlProperty(localName = "service-code")
    private String serviceCode;

    @JacksonXmlProperty(localName = "service-name")
    private String serviceName;

    @JacksonXmlProperty(localName = "price-details")
    private PriceDetails priceDetails;

    @JacksonXmlProperty(localName = "service-standard")
    private ServiceStandard serviceStandard;

    public String getServiceCode() {
      return serviceCode;
    }

    public String getServiceName() {
      return serviceName;
    }

    public PriceDetails getPriceDetails() {
      return priceDetails;
    }

    public ServiceStandard getServiceStandard() {
      return serviceStandard;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class PriceDetails {
    @JacksonXmlProperty(localName = "base")
    private double base;

    @JacksonXmlProperty(localName = "due")
    private double due;

    public double getBase() {
      return base;
    }

    public double getDue() {
      return due;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ServiceStandard {
    @JacksonXmlProperty(localName = "guaranteed-delivery")
    private boolean guaranteedDelivery;

    @JacksonXmlProperty(localName = "expected-delivery-date")
    private String expectedDeliveryDate;

    public boolean isGuaranteedDelivery() {
      return guaranteedDelivery;
    }

    public String getExpectedDeliveryDate() {
      return expectedDeliveryDate;
    }
  }

  public List<PriceQuote> getQuotes() {
    return quotes;
  }
}
