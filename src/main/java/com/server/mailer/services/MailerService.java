package com.server.mailer.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.server.mailer.config.MailerConfig;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

@Service
public class MailerService {

	@Autowired
	private MailerConfig mailerConfig;
	@Autowired
	private Environment env;

	private Logger logger = LoggerFactory.getLogger(MailerService.class);

  public boolean send(String emailTo, byte[] attachmentData, String attachmentName, String subject, String text){
		try {
			Session session = Session.getInstance(mailerConfig.getProperties(), new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailerConfig.getUsername(), mailerConfig.getPassword());
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailerConfig.getUsername()));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject(subject);

			getBytesOfTemplate();
			String htmlContent = "<h2>Hello, this letter from Andrew to Andrew :)</h2>";
	
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html");
	
			Multipart multipart = new MimeMultipart();

			MimeBodyPart attachmentPart = new MimeBodyPart();
			if(attachmentData != null && attachmentName != null){
				DataSource dataSource = new ByteArrayDataSource(attachmentData, "application/octet-stream");
				attachmentPart.setDataHandler(new DataHandler(dataSource));
				attachmentPart.setFileName(attachmentName);
				multipart.addBodyPart(attachmentPart);
			}

			multipart.addBodyPart(htmlPart);
	
			message.setContent(multipart);
	
			Transport.send(message);
			return true;
		} catch (MessagingException error) {
			String message = "An error occurred while sending email to customer !";
			logger.error(message, error);
			return false;
		}
	}

	private void getBytesOfTemplate(){
		String stringUrl = env.getProperty("email.templates.common");
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL resource = classLoader.getResource(stringUrl);
			
			if (resource == null) {
				throw new IllegalArgumentException("HTML template not found !");
			}
			
			String fileString = Files.readString(Path.of(resource.toURI()));

			System.out.println(fileString.replace("%s", "Hello Andrew"));
		} catch (IOException error) {
			logger.error("Error occured while reading HTML template for writing email letter to customer !", error);
		} catch (URISyntaxException error) {
			logger.error("Wrong URL to html template !", error);
		}
	}

}
