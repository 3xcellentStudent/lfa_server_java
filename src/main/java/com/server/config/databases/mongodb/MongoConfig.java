package com.server.config.databases.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.event.ClusterClosedEvent;
import com.mongodb.event.ClusterDescriptionChangedEvent;
import com.mongodb.event.ClusterListener;

@Configuration
public class MongoConfig {
  
  private int exceptionIndex = 0;
  private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

  @Value("${databases.mongodb.uri}")
  private String databaseUrl;

  @Bean
  public MongoClient mongoClient(){
    ConnectionString connectionString = new ConnectionString(databaseUrl);

    MongoClientSettings settings = MongoClientSettings.builder()
      .applyConnectionString(connectionString)
      .applyToClusterSettings(builder ->
        builder.addClusterListener(new ClusterListener() {
          @Override
          public void clusterClosed(ClusterClosedEvent event){
            logger.info(String.format("MongoDB cluster id: \"%s\" closed !", event.getClusterId().getValue()));
          }

          @Override
          public void clusterDescriptionChanged(ClusterDescriptionChangedEvent event){
            String currentClusterId = event.getClusterId().getValue();
            switch (event.getNewDescription().getType()){
              case UNKNOWN: {
                if(exceptionIndex > 0) logger.warn(String.format("MongoDB connection lost at cluster id: \"%s\" !", currentClusterId));
                exceptionIndex++;
              } case STANDALONE: {
                logger.info(String.format("MongoDB connection available at cluster id: \"%s\" ...", currentClusterId));
              } case REPLICA_SET, SHARDED: {
                logger.warn("Uknown event ! Add handler ...");
              }
            }
          }
        })
      )
      .build();

    return MongoClients.create(settings);
  }

  @Bean
  public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

}