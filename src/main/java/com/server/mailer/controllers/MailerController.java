package com.server.mailer.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mailer.dto.ReceivedPdfAndEmailDataDto;
import com.server.mailer.services.MailerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/mailer")
public class MailerController {

  private final Logger logger = LoggerFactory.getLogger(MailerController.class);

  @Autowired
  private MailerService mailerService;
  @Autowired
  private ObjectMapper objectMapper;

  @PostMapping("/send")
  public ResponseEntity<Object> sendEmail(@RequestBody String requestBodyString){
    try {
      ReceivedPdfAndEmailDataDto ReceivedPdfDocumentDto = objectMapper.readValue(requestBodyString, ReceivedPdfAndEmailDataDto.class);

      String fileName = ReceivedPdfDocumentDto.getFileName();
      byte[] fileBytes = ReceivedPdfDocumentDto.getFileBytes();
      ReceivedPdfAndEmailDataDto.EmailContent emailContent = ReceivedPdfDocumentDto.getEmailContent();

      mailerService.send(emailContent.customerEmail, fileBytes, fileName, "Email from me", "Hello, this is text for customer and I have to write good subject and letter content for customer !");
  
      return ResponseEntity.ok("ALALALALALALALALALLALALALALALAl");
    } catch (IOException error) {
      logger.error("An error occurred while processing I/O for some JSON.", error);
      return ResponseEntity.badRequest().body(error.getMessage());
    }
  }

}
