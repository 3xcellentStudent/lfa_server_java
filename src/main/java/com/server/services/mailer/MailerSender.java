package com.server.services.mailer;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailerSender {
  
  @Bean
  public static JavaMailSender mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.ukr.net");
    mailSender.setPort(2525);
    
    mailSender.setUsername("support.lamps-for-all@ukr.net");
    mailSender.setPassword("2aH4d0KjgWBlN1Ii");
    
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.enable", "true");
    props.put("mail.debug", "false");
    
    return mailSender;
  }
}