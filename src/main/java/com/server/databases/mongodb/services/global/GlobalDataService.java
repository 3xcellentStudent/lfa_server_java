package com.server.databases.mongodb.services.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.databases.mongodb.dto.categories.DeleteCategoriesDto;
import com.server.databases.mongodb.dto.categories.UpdateCategoriesDto;
// import com.server.databases.mongodb.helpers.queries.QueriesHelper;
import com.server.databases.mongodb.models.global.GlobalDataModel;
import com.server.databases.mongodb.services.uuid.CustomUUID;

@Service
public class GlobalDataService {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> createOne(String requestBodyString){
    try {
      GlobalDataModel requestBodyObject = objectMapper.readValue(requestBodyString, GlobalDataModel.class);
      String id = CustomUUID.fromString(requestBodyObject.getType());
      Query query = Query.query(Criteria.where("id").is(id));
      boolean isExists = mongoTemplate.exists(query, GlobalDataModel.class);

      if(isExists == false){
        long timestamp = System.currentTimeMillis();

        requestBodyObject.setId(id);
        requestBodyObject.setCreatedAt(timestamp);
        requestBodyObject.setUpdatedAt(timestamp);

        GlobalDataModel savedObject = mongoTemplate.save(requestBodyObject);

        String response = objectMapper.writeValueAsString(savedObject);
        return ResponseEntity.ok(response);
      } else {
        return ResponseEntity.status(409).body("This object with ID: " + id + " is exist !...");
      }
    } catch(Exception error){
      error.printStackTrace();
      System.err.println("Internal server error in class: " + this.getClass().getName() + "\n with error: " + error.getMessage());
      return null;
    }
  }

  public ResponseEntity<Object> categoryUpdateMany(UpdateCategoriesDto dto){
    Query query = Query.query(Criteria.where("id").is(dto.getId()));

    Update update = new Update();

    for(int i = 0; i < dto.getIndexes().size(); i++){
      Update setUpdate = update.set("categories." + dto.getCategoryName() + "." + dto.getIndexes().get(i), dto.getValues().get(i));

      mongoTemplate.updateFirst(query, setUpdate, GlobalDataModel.class);
    }

    return ResponseEntity.ok("Categories have been updated !");
  }

  public ResponseEntity<Object> categoryDeleteMany(DeleteCategoriesDto dto){
    Query query = Query.query(Criteria.where("id").is(dto.getId()));

    Update update = new Update();

    for(int i = 0; i < dto.getIndexes().size(); i++){
      Update unsetUpdate = update.unset("categories." + dto.getCategoryName() + "." + dto.getIndexes().get(i));

      mongoTemplate.updateFirst(query, unsetUpdate, GlobalDataModel.class);
    }

    return ResponseEntity.ok("Categories have been deleted !");
  }
  
}
