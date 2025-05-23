package com.server.mailer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.mailer.MailerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mailer/")
public class MailerController {

  @Autowired
  private MailerService mailerService;

  @PostMapping("/send")
  public void send(@RequestBody String requestBodyString){
    System.out.println(requestBodyString);
    // mailerService.send("lamps.for.all00@gmail.com", null, null, 
    // "Something from me", "HAHAHAHAHA it's me !!!");
  }

}
