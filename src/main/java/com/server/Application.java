package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.resources.redis.services.PortChecker;
import com.server.services.firebase.FirebaseInitialize;

@SpringBootApplication
public class Application {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
		new FirebaseInitialize().initialize();
		// PortChecker.checkPort();
	}
}