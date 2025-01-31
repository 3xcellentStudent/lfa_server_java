package com.server.services.mongodb.services.media.diffusers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.services.mongodb.repositories.media.diffusers.DiffusersMediaRepository;

@Service
public class DiffusersMediaService {

  @Autowired
  private DiffusersMediaRepository mediaRepository;
  @Autowired
  private DiffusersMediaModel mediaModel;

  public ResponseEntity<Object> createOne(String _id, String parent_id, String reviews_id, long timestamp){
    try {
      mediaModel.setParentId(parent_id );
      mediaModel.setReviewsId(reviews_id);
      mediaModel.setId(_id);
      mediaModel.setCreateTime(timestamp);
      mediaModel.setUpdateTime(timestamp);

      DiffusersMediaModel savedReviews = mediaRepository.save(mediaModel);

      return ResponseEntity.ok(savedReviews);
    } catch(IllegalArgumentException error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> createOne(DiffusersMediaModel requestBody){
    String _id = UUID.randomUUID().toString();
    long timestamp = System.currentTimeMillis();

    try {
      requestBody.setId(_id);
      requestBody.setCreateTime(timestamp);
      requestBody.setUpdateTime(timestamp);

      DiffusersMediaModel savedReviews = mediaRepository.save(requestBody);

      return ResponseEntity.ok(savedReviews);
    } catch(IllegalArgumentException error){
      System.err.println("internal server error: " + error.getMessage());
      error.printStackTrace();
      return null;
    }
  }

  public ResponseEntity<Object> findById(Iterable<String> _ids){
    try {
      List<DiffusersMediaModel> foundMedia = mediaRepository.findAllById(_ids).stream()
      .filter(Objects::nonNull).toList();

      return ResponseEntity.ok(foundMedia);
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return null;
    }
  }
}
