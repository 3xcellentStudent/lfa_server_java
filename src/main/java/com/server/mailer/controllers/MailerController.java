package com.server.mailer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.mailer.services.MailerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mailer")
public class MailerController {

  private final Logger logger = LoggerFactory.getLogger(MailerController.class);

  @Autowired
  private MailerService mailerService;

  @PostMapping("/send")
  public void sendEmail(@RequestBody String requestBodyString){
    System.out.println(requestBodyString);
    // mailerService.send("lamps.for.all00@gmail.com", null, null, 
    // "Something from me", "HAHAHAHAHA it's me !!!");
  }

}
