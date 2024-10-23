package com.server.services.mailer;

import java.util.Properties;

public class MailerConfig {
  
  public static Properties getProperties(){
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.host", "smtp.gmail.com");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    
    return properties;
  }

  public static String getUsername(){return "lamps.for.all00@gmail.com";}

  public static String getPassword(){return "slgifhmoptjpqkpu";}
}