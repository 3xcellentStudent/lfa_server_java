package com.server.services.mailer;

import org.springframework.http.ResponseEntity;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

public class MailerHOCs {
  // private JavaMailSender mailSender = MailerSender.mailSender();

  public static boolean sendMailHOC(String emailTo, String emailFrom, String subject, String text){
		// SimpleMailMessage message = new SimpleMailMessage();
		// message.setTo(emailTo);
		// message.setFrom(emailFrom);
		// message.setText(text);
		// message.setSubject(subject);
		// try {
		// 	new MailerHOCs().mailSender.send(message);
		// 	return true;
		// } catch(Exception err){return false;}
		JavaMailSender sender = MailerSender.mailSender();
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(emailFrom);
			helper.setTo(emailTo);
			helper.setSubject(subject);
			helper.setText("<html><body><span style=\"color: blue; text-transform: uppercase;\">" + text + "</span></body></html>", true);
			sender.send(message);
			return true;	
		} catch(Exception err){
			System.out.println(err);
			return  false;
		}
	}

	public static ResponseEntity<String> statusHOC(boolean sendStatus, String email){
    if(sendStatus) return ResponseEntity.status(202).body("Letter was sent to: " + email);
    else return ResponseEntity.status(500).body("Failed to send mail!");
	}
}
