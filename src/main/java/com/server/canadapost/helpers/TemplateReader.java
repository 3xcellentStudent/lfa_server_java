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

import com.server.canadapost.models.ShipPriceRequest;

@Service
public class TemplateReader {

  private final Logger logger = LoggerFactory.getLogger(TemplateReader.class);
	
	@Value("${delivery.canadapost.templates.common}")
	private String stringUrl;
	@Value("${delivery.canadapost.account.number}")
	private String accountNumber;

  public String getTemplateAsString(String templateName, ShipPriceRequest body){
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(stringUrl + templateName);
			
			if (resource == null) {
				throw new IllegalArgumentException("HTML template not found !");
			}
			
			String fileString = Files.readString(Path.of(resource.toURI()));

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
		} catch (IOException error) {
			logger.error("Error occured while reading HTML template for writing email letter to customer !", error);
      return null;
		} catch (URISyntaxException error) {
			logger.error("Wrong URL to html template !", error);
      return null;
		}
	}
}
