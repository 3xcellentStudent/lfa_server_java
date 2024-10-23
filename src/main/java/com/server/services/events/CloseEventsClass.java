package com.server.services.events;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.server.services.redis.helpers.RunTimeFile;

// @Component
// public class CloseEventsClass implements ApplicationListener<ContextClosedEvent>{

//   @Override
//   public void onApplicationEvent(ContextClosedEvent event){
//     String resourcePath = this.getClass().getResource("").getPath();
//     String relativePath = "../../../resources/redis/files.bat/redis.stop.bat";
//     Path classPath = Paths.get(resourcePath.substring(3));
//     Path pathToStopRedisFile = classPath.resolve(relativePath).normalize();   
//     String stopRedisFilePath = pathToStopRedisFile.toAbsolutePath().toString();
//     RunTimeFile.run(stopRedisFilePath);
//     System.out.println("Spring Boot server is shutting down...");
//   }
// }

public class CloseEventsClass{
}
