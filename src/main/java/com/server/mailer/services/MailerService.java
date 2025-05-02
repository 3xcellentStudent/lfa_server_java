package com.server.mailer.services;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.server.mailer.MailerConfig;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
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
		} catch (Exception error) {
			System.out.println(error.getMessage());
			error.printStackTrace();
			return false;
		}
	}
}
