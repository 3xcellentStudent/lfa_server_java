package com.server.databases.mongodb.services.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.models.media.MediaModel;

@Service
public class MediaService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public ResponseEntity<Object> createOne(String id, String parentId, long timestamp, String collectionName){
    MediaModel newMediaObject = new MediaModel();

    newMediaObject.setId(id);
    newMediaObject.setParentId(parentId);
    // newObject.setReviewsId(reviews_id);
    newMediaObject.setCreateAt(timestamp);
    newMediaObject.setUpdateAt(timestamp);

    MediaModel response = mongoTemplate.save(newMediaObject, collectionName);

    return ResponseEntity.ok(response);
  }

  public ResponseEntity<Object> createOne(MediaModel body, String collectionName){
    long timestamp = System.currentTimeMillis();

    // MediaModel requestBodyObject = objectMapper.readValue(requestBodyString, MediaModel.class);
    String id = body.getId();

    Query query = Query.query(Criteria.where("id").is(id));

    boolean isExists = mongoTemplate.exists(query, MediaModel.class, collectionName);
    if(isExists){
      return ResponseEntity.status(303).body("Document already exists !");
    } else {
      body.setId(id);
      body.setCreateAt(timestamp);
      body.setUpdateAt(timestamp);

      MediaModel savedReviews = mongoTemplate.save(body, collectionName);

      return ResponseEntity.ok(savedReviews);
    }
  }

}
