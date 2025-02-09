package com.server.services.mongodb.services.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.services.mongodb.helpers.queries.QueriesHelper;
import com.server.services.mongodb.models.global.GlobalDataModel;
import com.server.services.mongodb.services.MainService;
import com.server.services.mongodb.services.uuid.CustomUUID;

@Service
public class GlobalDataService {

  @Autowired
  private GlobalDataModel globalDataModel;
  @Autowired
  private MainService mainService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createOne(String requestBodyString){
    try {
      GlobalDataModel requestBodyObject = objectMapper.readValue(requestBodyString, GlobalDataModel.class);
      String id = CustomUUID.fromString(requestBodyObject.shortDescription);
      boolean isExists = mongoTemplate.exists(QueriesHelper.getId("id", id), GlobalDataModel.class);

      if(isExists == false){
        long timestamp = System.currentTimeMillis();

        requestBodyObject.setId(id);
        requestBodyObject.setCreatedAt(timestamp);
        requestBodyObject.setUpdatedAt(timestamp);

        GlobalDataModel savedObject = mongoTemplate.save(requestBodyObject);

        String stringResponse = objectMapper.writeValueAsString(savedObject);
        return ResponseEntity.ok(stringResponse);
      } else {
        return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
      }
    } catch(Exception error){
      error.printStackTrace();
      System.err.println("Internal server error in class: " + this.getClass().getName() + "\n with error: " + error.getMessage());
      return null;
    }
  }

}
