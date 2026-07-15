package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// import com.resources.redis.services.PortChecker;
// import com.server.services.firebase.FirebaseInitialize;
@SpringBootApplication
@EnableMongoRepositories
@EnableAsync
@EnableScheduling
public class Application {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);

		// new FirebaseInitialize().initialize();
		// PortChecker.checkPort();
	}
}