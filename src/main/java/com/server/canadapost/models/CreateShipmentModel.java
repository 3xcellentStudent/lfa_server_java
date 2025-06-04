package com.server.canadapost.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateShipmentModel {

  @JsonProperty("requestedShippingPoint")
  private String requestedShippingPoint;

  @JsonProperty("serviceCode")
  private String serviceCode;

  @JsonProperty("sender")
  private Party sender;

  @JsonProperty("destination")
  private Party destination;

  @JsonProperty("options")
  private List<Option> options;

  @JsonProperty("parcelCharacteristics")
  private ParcelCharacteristics parcelCharacteristics;

  @JsonProperty("preferences")
  private Preferences preferences;

  public static class Party {
    private String name;
    private String company;
    private String contactPhone;
    private AddressDetails addressDetails;
  }

  public static class AddressDetails {
    private String addressLine1;
    private String city;
    private String provState;
    private String countryCode;
    private String postalZipCode;
  }

  public static class Option {
    private String optionCode;
  }

  public static class ParcelCharacteristics {
    private double weight;
    private Dimensions dimensions;
  }

  public static class Dimensions {
    private double length;
    private double width;
    private double height;
  }

  public static class Preferences {
    private boolean showPackingInstructions;
  }

}
