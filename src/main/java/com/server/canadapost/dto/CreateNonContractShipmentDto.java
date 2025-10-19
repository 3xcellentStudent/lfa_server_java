package com.server.canadapost.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateNonContractShipmentDto {

  @JsonProperty private String requestedShippingPoint;
  @JsonProperty private DeliverySpec deliverySpec;
  @JsonProperty private String createPublicKey;
  @JsonProperty private String createQRCode;

  public static class DeliverySpec {
    @JsonProperty private String serviceCode;
    @JsonProperty private Sender sender;
    @JsonProperty private Destination destination;
    @JsonProperty private List<Option> options;
    @JsonProperty private ParcelCharacteristics parcelCharacteristics;
    @JsonProperty private Preferences preferences;

    public String getServiceCode() {
      return serviceCode;
    }

    public Sender getSender() {
      return sender;
    }

    public Destination getDestination() {
      return destination;
    }

    public List<Option> getOptions() {
      return options;
    }

    public ParcelCharacteristics getParcelCharacteristics() {
      return parcelCharacteristics;
    }

    public Preferences getPreferences() {
      return preferences;
    }

  }

  public static class Sender {
    @JsonProperty private String company;
    @JsonProperty private String contactPhone;
    @JsonProperty private AddressDetails addressDetails;

    public String getCompany() {
      return company;
    }

    public String getContactPhone() {
      return contactPhone;
    }

    public AddressDetails getAddressDetails() {
      return addressDetails;
    }

    public static class AddressDetails {
      @JsonProperty private String addressLine1;
      @JsonProperty private String city;
      @JsonProperty private String provState;
      @JsonProperty private String postalZipCode;

      public String getAddressLine1() {
        return addressLine1;
      }

      public String getCity() {
        return city;
      }

      public String getProvState() {
        return provState;
      }

      public String getPostalZipCode() {
        return postalZipCode;
      }

    }
  }

  public static class Destination {
    @JsonProperty private String name;
    @JsonProperty private String company;
    @JsonProperty private AddressDetails addressDetails;

    public String getName() {
      return name;
    }

    public String getCompany() {
      return company;
    }

    public AddressDetails getAddressDetails() {
      return addressDetails;
    }

    public static class AddressDetails {
      @JsonProperty private String addressLine1;
      @JsonProperty private String city;
      @JsonProperty private String provState;
      @JsonProperty private String postalZipCode;
      @JsonProperty private String countryCode;

      public String getAddressLine1() {
        return addressLine1;
      }

      public String getCity() {
        return city;
      }

      public String getProvState() {
        return provState;
      }

      public String getPostalZipCode() {
        return postalZipCode;
      }

      public String getCountryCode() {
        return countryCode;
      }
    }
  }

  public static class Option {
    @JsonProperty private String optionCode;

    public String getOptionCode() {
      return optionCode;
    }
  }

  public static class ParcelCharacteristics {
    @JsonProperty private double weight;
    @JsonProperty private Dimensions dimensions;

    public double getWeight() {
      return weight;
    }

    public Dimensions getDimensions() {
      return dimensions;
    }
  }

  public static class Dimensions {
    @JsonProperty private double length;
    @JsonProperty private double width;
    @JsonProperty private double height;

    public double getLength() {
      return length;
    }

    public double getWidth() {
      return width;
    }

    public double getHeight() {
      return height;
    }
  }

  public static class Preferences {
    @JsonProperty private boolean showPackingInstructions;

    public boolean isShowPackingInstructions() {
      return showPackingInstructions;
    }
  }

  public CreateNonContractShipmentDto(String requestedShippingPoint, DeliverySpec deliverySpec, String createPublicKey, String createQRCode){
    this.requestedShippingPoint = requestedShippingPoint;
    this.deliverySpec = deliverySpec;
    this.createPublicKey = createPublicKey;
    this.createQRCode = createQRCode;
  }

  public CreateNonContractShipmentDto(){
  }

  public String getRequestedShippingPoint() {
    return requestedShippingPoint;
  }

  public DeliverySpec getDeliverySpec() {
    return deliverySpec;
  }

  public String getCreatePublicKey() {
    return createPublicKey;
  }

  public String getCreateQRCode() {
    return createQRCode;
  }

}
