package com.server.services.mongodb.models.media.diffusers;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel.MediaContent.Image;
import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel.MediaContent.TitleContent;

@Component
@Document(collection = "media_diffusers")
public class DiffusersMediaModel {

  @Id
  private String id;
  private String parentId;
  private String reviewsId;
  private long createdAt;
  private long updatedAt;
  public TitleContent titleContent;
  public ArrayList<ArrayList<Image>> images = new ArrayList<>();


  public DiffusersMediaModel(){}

  public DiffusersMediaModel(DiffusersMediaModel dataModel){
    this.titleContent = dataModel.titleContent;
    this.images = dataModel.images;
  }
  
  public static class MediaContent {
    public TitleContent titleContent;
    public ArrayList<ArrayList<Image>> images;

    public static class TitleContent {
      public String productLogo;
      public String descriptionVideo;
    }

    public static class Image {
      public String media;
      public String src;
    }
  }

  public void setId(String id){
    this.id = id;
  }

  public void setReviewsId(String reviewsId){
    this.reviewsId = reviewsId;
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

  public String getReviewsId(){
    return this.reviewsId;
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
}
