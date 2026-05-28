package com.server.canadapost.helpers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.server.canadapost.dto.CreateNonContractShipmentDto;
import com.server.canadapost.dto.CreatePickupRequestDto;
import com.server.canadapost.dto.PickupRequestPriceDto;
import com.server.canadapost.dto.ShipPriceDto;

// @Service
public class TemplateReader {

  private final Logger logger = LoggerFactory.getLogger(TemplateReader.class);
	
	@Value("${delivery.canadapost.templates.path.common}")
	private String templatesPath;
	@Value("${delivery.canadapost.account.number}")
	private String accountNumber;

	private <T> String getXmlFormattedString(String templateName, T body){
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(templatesPath + templateName);
			
			if(resource == null){
				logger.error(String.format("Template with filename \"%s\" not found !", templateName));
				return null;
			}
			
			String fileString = Files.readString(Path.of(resource.toURI()));

			return fileString;
		} catch(IOException error){
			String message = String.format("Error occured while reading XML template with filename \"%s\" !", templateName);
			logger.error(message, error);
			return message;
		} catch (URISyntaxException error) {
			String message = String.format("Wrong URL to xml template with filename \"%s\" !", templateName);
			logger.error(message, error);
			return message;
		}
	}

  public String getTemplateAsString(String templateName, ShipPriceDto body){
		String fileString = getXmlFormattedString(templateName, body);

		if(fileString == null) return null;

		String formatted = String.format(
			fileString, 
			accountNumber, 
			body.getWeight(), 
			body.getLength(), 
			body.getWidth(), 
			body.getHeight(), 
			body.getOriginPostalCode(), 
			body.getDestinationPostalCode()
		);

		return formatted;
	}

	public String getTemplateAsString(String templateName, PickupRequestPriceDto body){
		String fileString = getXmlFormattedString(templateName, body);

		if(fileString == null) return null;

		String formatted = String.format(
			fileString, 
			body.getDate(), 
			accountNumber, 
			body.getPriorityFlag(), 
			body.getAlternateAddressPostalCode()
		);

		return formatted;
	}

	public String getTemplateAsString(String templateName, CreatePickupRequestDto body){
		String fileString = getXmlFormattedString(templateName, body);

		if(fileString == null) return null;

		String formatted = String.format(
			fileString, 
			body.getPickupLocation().isBusinessAddressFlag(),
			body.getContactInfo().getContactName(),
			body.getContactInfo().getEmail(),
			body.getContactInfo().getContactPhone(),
			body.getContactInfo().isReceiveEmailUpdatesFlag(),
			body.getLocationDetails().isFiveTonFlag(),
			body.getLocationDetails().isLoadingDockFlag(),
			body.getLocationDetails().getPickupInstructions(),
			body.getItemsCharacteristics().isPriorityFlag(),
			body.getItemsCharacteristics().isReturnsFlag(),
			body.getItemsCharacteristics().isHeavyItemFlag(),
			body.getPickupVolume(),
			body.getPickupTimes().getOnDemandPickupTime().getDate(),
			body.getPickupTimes().getOnDemandPickupTime().getPreferredTime(),
			body.getPickupTimes().getOnDemandPickupTime().getClosingTime()
		);

		return formatted;
	}

	public String getTemplateAsString(String templateName, CreateNonContractShipmentDto body){
		String fileString = getXmlFormattedString(templateName, body);

		if(fileString == null) return null;

		// Add formatting logic here based on the fields in CreateNonContractShipmentDto
		// This is a placeholder example; adjust according to actual template requirements

		CreateNonContractShipmentDto.Sender sender = body.getDeliverySpec().getSender();
		CreateNonContractShipmentDto.Destination destination = body.getDeliverySpec().getDestination();
		CreateNonContractShipmentDto.ParcelCharacteristics parcelCharacteristics = body.getDeliverySpec().getParcelCharacteristics();

		String formatted = String.format(
			fileString,
			body.getRequestedShippingPoint(),
			body.getDeliverySpec().getServiceCode(),
			sender.getCompany(),
			sender.getContactPhone(),
			sender.getAddressDetails().getAddressLine1(),
			sender.getAddressDetails().getCity(),
			sender.getAddressDetails().getProvState(),
			sender.getAddressDetails().getPostalZipCode(),
			destination.getName(),
			destination.getCompany(),
			destination.getAddressDetails().getAddressLine1(),
			destination.getAddressDetails().getCity(),
			destination.getAddressDetails().getProvState(),
			destination.getAddressDetails().getCountryCode(),
			destination.getAddressDetails().getPostalZipCode(),
			body.getDeliverySpec().getOptions() != null ? body.getDeliverySpec().getOptions().get(0).getOptionCode() : "",
			parcelCharacteristics.getWeight(),
			parcelCharacteristics.getDimensions().getLength(),
			parcelCharacteristics.getDimensions().getWidth(),
			parcelCharacteristics.getDimensions().getHeight(),
			body.getDeliverySpec().getPreferences() != null ? body.getDeliverySpec().getPreferences().isShowPackingInstructions() : false,
			body.getCreatePublicKey(),
			body.getCreateQRCode()
		);

		return formatted;
	}

}
