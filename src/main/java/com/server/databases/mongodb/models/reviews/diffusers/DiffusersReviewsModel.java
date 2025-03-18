package com.server.databases.mongodb.models.reviews.diffusers;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "reviews_diffusers")
public class DiffusersReviewsModel {

  @Id
  private String id;
  private String parentId;
  public String content;
  public String firstName;
  public String lastName;
  public String title;
  public int rating;
  public List<String> attachments;
  private long createdAt;
  private long updatedAt;

  public DiffusersReviewsModel(){}
  
  public DiffusersReviewsModel(DiffusersReviewsModel dataModel){
    this.content = dataModel.content;
    this.firstName = dataModel.firstName;
    this.lastName = dataModel.lastName;
    this.title = dataModel.title;
    this.rating = dataModel.rating;
    this.attachments = dataModel.attachments;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public void setParentId(String parentId){
    this.parentId = parentId;
  }

  public String getParentId(){
    return this.parentId;
  }

  public long getCreateAt(){
    return this.createdAt;
  }

  public void setCreateAt(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdateAt(){
    return this.updatedAt;
  }

  public void setUpdateAt(long newTime){
    this.updatedAt = newTime;
  }

  public String getCollectionName(){
    return "reviews_diffusers";
  }

}
