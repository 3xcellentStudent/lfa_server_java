package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.server.services.firebase.FirebaseInitialize;
import com.server.services.paypal.PayPalAuth;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		new FirebaseInitialize().initialize();;
		new PayPalAuth().callAuth();
	}
}