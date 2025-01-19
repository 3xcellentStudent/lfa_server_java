package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.resources.redis.services.PortChecker;
import com.server.services.firebase.FirebaseInitialize;
import com.server.services.mongodb.config.MongoClientConnection;
@SpringBootApplication
public class Application {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
		MongoClientConnection.start();		
		// new FirebaseInitialize().initialize();
		// PortChecker.checkPort();
	}
}