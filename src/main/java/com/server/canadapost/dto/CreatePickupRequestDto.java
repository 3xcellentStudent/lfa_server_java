package com.server.canadapost.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePickupRequestDto {

  @JsonProperty private String pickupType;
  @JsonProperty private PickupLocation pickupLocation;
  @JsonProperty private ContactInfo contactInfo;
  @JsonProperty private LocationDetails locationDetails;
  @JsonProperty private ItemsCharacteristics itemsCharacteristics;
  @JsonProperty private String pickupVolume;
  @JsonProperty private PickupTimes pickupTimes;

  public String getPickupType() {
    return pickupType;
  }

  public PickupLocation getPickupLocation() {
    return pickupLocation;
  }

  public ContactInfo getContactInfo() {
    return contactInfo;
  }

  public LocationDetails getLocationDetails() {
    return locationDetails;
  }

  public ItemsCharacteristics getItemsCharacteristics() {
    return itemsCharacteristics;
  }

  public String getPickupVolume() {
    return pickupVolume;
  }

  public PickupTimes getPickupTimes() {
    return pickupTimes;
  }

  public static class PickupLocation {
    @JsonProperty private boolean businessAddressFlag;

    public boolean isBusinessAddressFlag() {
      return businessAddressFlag;
    }
  }

  public static class ContactInfo {
    @JsonProperty private String contactName;
    @JsonProperty private String email;
    @JsonProperty private String contactPhone;
    @JsonProperty private boolean receiveEmailUpdatesFlag;

    public String getContactName() {
      return contactName;
    }

    public String getEmail() {
      return email;
    }

    public String getContactPhone() {
      return contactPhone;
    }

    public boolean isReceiveEmailUpdatesFlag() {
      return receiveEmailUpdatesFlag;
    }
  }

  public static class LocationDetails {
    @JsonProperty private boolean fiveTonFlag;
    @JsonProperty private boolean loadingDockFlag;
    @JsonProperty private String pickupInstructions;

    public boolean isFiveTonFlag() {
      return fiveTonFlag;
    }

    public boolean isLoadingDockFlag() {
      return loadingDockFlag;
    }

    public String getPickupInstructions() {
      return pickupInstructions;
    }
  }

  public static class ItemsCharacteristics {
    @JsonProperty private boolean priorityFlag;
    @JsonProperty private boolean returnsFlag;
    @JsonProperty private boolean heavyItemFlag;

    public boolean isPriorityFlag() {
      return priorityFlag;
    }

    public boolean isReturnsFlag() {
      return returnsFlag;
    }

    public boolean isHeavyItemFlag() {
      return heavyItemFlag;
    }
  }

  public static class PickupTimes {
    @JsonProperty private OnDemandPickupTime onDemandPickupTime;

    public OnDemandPickupTime getOnDemandPickupTime() {
      return onDemandPickupTime;
    }
  }

  public static class OnDemandPickupTime {
    @JsonProperty private String date;
    @JsonProperty private String preferredTime;
    @JsonProperty private String closingTime;

    public String getDate() {
      return date;
    }

    public String getPreferredTime() {
      return preferredTime;
    }

    public String getClosingTime() {
      return closingTime;
    }
  }

}
