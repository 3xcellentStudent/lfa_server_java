package com.server.config.services.databases.mongodb;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.event.ClusterDescriptionChangedEvent;
import com.mongodb.event.ClusterListener;

@Configuration
public class MongoConfig {
  private int exceptionIndex = 0;
  private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

  @Bean
  public MongoClient mongoClient(){
    MongoClientSettings settings = MongoClientSettings.builder()
    .applyConnectionString(new ConnectionString("mongodb://localhost:27017/storetest"))
    .applyToClusterSettings(builder -> 
      builder.serverSelectionTimeout(10, TimeUnit.SECONDS)
      .addClusterListener(new ClusterListener(){
        @Override
        public void clusterDescriptionChanged(ClusterDescriptionChangedEvent event){
          switch (event.getNewDescription().getType()){
            case UNKNOWN: {
              if(exceptionIndex > 0){
                logger.error("MongoDB connection lost!");
                exceptionIndex++;
              }

              exceptionIndex++;
              break;
            }
            case SHARDED:
            case REPLICA_SET:
            case STANDALONE: {
              logger.info("MongoDB cluster status changed: " + event.getNewDescription().getType());
              break;
            }
          }
        }
    })
  )
  .build();

    return MongoClients.create(settings);
  }

}