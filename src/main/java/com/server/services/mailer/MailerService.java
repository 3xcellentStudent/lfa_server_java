package com.server.services.mailer;

import java.util.Properties;

import org.springframework.http.ResponseEntity;
// import org.springframework.mail.SimpleMailMessage;

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

public class MailerService {
  // private JavaMailSender mailSender = MailerSender.mailSender();

  public static boolean send(String emailTo, String emailFrom, byte[] attachmentData, String attachmentName, String subject, String text){
		try {
			Properties properties = MailerConfig.getProperties();
			// Properties props = new Properties();
			// props.setProperty("mail.transport.protocol", "smtp");
			// props.setProperty("mail.host", "smtp.gmail.com");
			// props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.port", "587");
			// props.put("mail.smtp.starttls.enable", "true");
			// props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	
			Session session = Session.getInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(MailerConfig.getUsername(), MailerConfig.getPassword());
				}
			});
	
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("lamps.for.all00@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject(subject);

			String htmlContent = "<h2>Hello, this letter from Andrew to Andrew :)</h2>";
	
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlContent, "text/html");
	
			MimeBodyPart attachmentPart = new MimeBodyPart();
			DataSource dataSource = new ByteArrayDataSource(attachmentData, "application/octet-stream");
			attachmentPart.setDataHandler(new DataHandler(dataSource));
			attachmentPart.setFileName(attachmentName);
	
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(htmlPart);
			multipart.addBodyPart(attachmentPart);
	
			message.setContent(multipart);
	
			Transport.send(message);
			return true;
		} catch (Exception error) {
			System.out.println(error);
			error.printStackTrace();
			return false;
		}
	}

	public static ResponseEntity<String> statusHOC(boolean sendStatus, String email){
    if(sendStatus) return ResponseEntity.status(202).body("Letter was sent to: " + email);
    else return ResponseEntity.status(500).body("Failed to send mail!");
	}
}
