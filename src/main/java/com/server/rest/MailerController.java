package com.server.rest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.mailer.MailerHOCs;
import com.server.services.mailer.MailerService;
// import com.server.services.openai.OpenAIServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MailerController {
  private final String ourMail = "support.lamps-for-all@ukr.net";

  @PostMapping("/mail/support")
  public ResponseEntity<String> support(@RequestBody String body){
    String emailFrom = new JSONObject(body).get("emailFrom").toString();
    String text = new JSONObject(body).get("text").toString();
    // String openAIRes = new OpenAIServices().sendMessage(message);
    boolean sendStatus = MailerService.callSendMailHOC(ourMail, emailFrom, "SUPPORT", text);
    return MailerHOCs.statusHOC(sendStatus, emailFrom);
  }

  @PostMapping("/mail/payment")
  public ResponseEntity<String> payment(@RequestBody String body){
    String emailTo = new JSONObject(body).get("emailTo").toString();
    String text = new JSONObject(body).get("text").toString();
    // String openAIRes = new OpenAIServices().sendMessage(message);
    boolean sendStatus = MailerService.callSendMailHOC(emailTo, ourMail, "PAYMENT", text);
    return MailerHOCs.statusHOC(sendStatus, emailTo);
  }

  @PostMapping("/mail/order")
  public ResponseEntity<String> order(@RequestBody String body){
    String emailTo = new JSONObject(body).get("emailTo").toString();
    String text = new JSONObject(body).get("text").toString();
    // String openAIRes = new OpenAIServices().sendMessage(message);
    boolean sendStatus = MailerService.callSendMailHOC(emailTo, ourMail, "STATUS ORDER", text);
    return MailerHOCs.statusHOC(sendStatus, emailTo);
  }
}