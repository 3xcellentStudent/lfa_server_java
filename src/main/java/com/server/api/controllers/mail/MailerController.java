package com.server.api.controllers.mail;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mailer.MailerService;
import com.server.services.mailer.MailerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MailerController {
  // private final String ourMail = "support.lamps-for-all@ukr.net";

  // @PostMapping("/mail/support")
  // public ResponseEntity<String> support(@RequestBody String body){
  //   String emailFrom = new JSONObject(body).get("emailFrom").toString();
  //   String text = new JSONObject(body).get("text").toString();
  //   // String openAIRes = new OpenAIServices().sendMessage(message);
  //   boolean sendStatus = MailerService.send(ourMail, emailFrom, "SUPPORT", text);
  //   return MailerService.statusHOC(sendStatus, emailFrom);
  // }

  // @PostMapping("/mail/payment")
  // public ResponseEntity<String> payment(@RequestBody String body){
  //   String emailTo = new JSONObject(body).get("emailTo").toString();
  //   String text = new JSONObject(body).get("text").toString();
  //   // String openAIRes = new OpenAIServices().sendMessage(message);
  //   boolean sendStatus = MailerService.callSendMailHOC(emailTo, ourMail, "PAYMENT", text);
  //   return MailerService.statusHOC(sendStatus, emailTo);
  // }

  // @PostMapping("/mail/order")
  // public ResponseEntity<String> order(@RequestBody String body){
  //   String emailTo = new JSONObject(body).get("emailTo").toString();
  //   String text = new JSONObject(body).get("text").toString();
  //   // String openAIRes = new OpenAIServices().sendMessage(message);
  //   boolean sendStatus = MailerService.callSendMailHOC(emailTo, ourMail, "STATUS ORDER", text);
  //   return MailerService.statusHOC(sendStatus, emailTo);
  // }
}