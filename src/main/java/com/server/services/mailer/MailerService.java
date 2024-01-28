package com.server.services.mailer;

// import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailerService {

	public static boolean callSendMailHOC(String emailTo, String emailFrom, String subject, String text){
		return MailerHOCs.sendMailHOC(emailTo, emailFrom, subject, text);
	}

	// public static JSONObject resMailJSON(String status, String message){
  //   return new JSONObject().put("status", status).put("message", message);
  // }
}