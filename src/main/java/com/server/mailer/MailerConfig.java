package com.server.mailer;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MailerConfig {

  @Autowired
	private Environment env;
  
  // public Properties getProperties(){
    // Properties properties = new Properties();
    // properties.setProperty("mail.transport.protocol", "smtp");
    // properties.setProperty("mail.host", "smtp.gmail.com");
    // properties.put("mail.smtp.auth", "true");
    // properties.put("mail.smtp.port", "587");
    // properties.put("mail.smtp.starttls.enable", "true");
    // properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");


  //   Properties properties = new Properties();
  //   properties.setProperty("mail.transport.protocol", mailerConfigProperties.getTransportProtocol());
  //   properties.setProperty("mail.host", mailerConfigProperties.getHost());
  //   properties.put("mail.smtp.auth", mailerConfigProperties.getAuth());
  //   properties.put("mail.smtp.port", mailerConfigProperties.getPort());
  //   properties.put("mail.smtp.starttls.enable", mailerConfigProperties.getStarttlsEnable());
  //   properties.put("mail.smtp.ssl.trust", mailerConfigProperties.getSslTrust());
    
  //   return properties;
  // }

  // public static String getUsername(){return "lamps.for.all00@gmail.com";}

  // public static String getPassword(){return "slgifhmoptjpqkpu";}

  public MailerConfig(){}

  public Properties getProperties(){
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", env.getProperty("gmail.transport.protocol"));
    properties.setProperty("mail.host", env.getProperty("gmail.host"));
    properties.put("mail.smtp.auth", env.getProperty("gmail.smtp.auth"));
    properties.put("mail.smtp.port", env.getProperty("gmail.smtp.port"));
    properties.put("mail.smtp.starttls.enable", env.getProperty("gmail.smtp.starttls.enable"));
    properties.put("mail.smtp.ssl.trust", env.getProperty("gmail.smtp.ssl.trust"));

    return properties;
  }


  public String getTransportProtocol() {
    return env.getProperty("gmail.transport.protocol");
  }

  public String getHost() {
    return env.getProperty("gmail.host");
  }

  public String getAuth() {
    return env.getProperty("gmail.smtp.auth");
  }

  public String getPort() {
    return env.getProperty("gmail.smtp.port");
  }

  public String getStartTlsEnable() {
    return env.getProperty("gmail.smtp.starttls.enable");
  }

  public String getSslTrust() {
    return env.getProperty("gmail.smtp.ssl.trust");
  }

  public String getUsername() {
    return env.getProperty("gmail.username");
  }

  public String getPassword() {
    return env.getProperty("gmail.password");
  }

}