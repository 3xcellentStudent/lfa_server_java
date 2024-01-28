package com.server.rest;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.firebase.FirebaseService;
import com.server.services.mailer.MailerHOCs;
import com.server.services.mailer.MailerService;
// import com.server.services.openai.OpenAIServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewsController {

  @PostMapping("/reviews")
  public ResponseEntity<String> review(@RequestBody String body){
    String ourMail = "support.lamps-for-all@ukr.net";
    String emailTo = new JSONObject(body).get("emailTo").toString();
    String text = new JSONObject(body).get("text").toString();
    // String openAIRes = new OpenAIServices().sendMessage(message);
    int statusAddedToDB = new FirebaseService().postDocument(emailTo, text);
    switch (statusAddedToDB) {
      case 200: {
        boolean sendStatus = MailerService.callSendMailHOC(emailTo, ourMail, "REVIEW", text);
        return MailerHOCs.statusHOC(sendStatus, emailTo);
      }
      default: return ResponseEntity.status(500).body("Something went wrong!");
    }
  }

  @GetMapping("/reviews")
  public static void getReviewId(@RequestParam String id){
    if(id != "") System.out.println(id);
    else System.out.println("nothing");
  }
}