package com.server.services.mongodb.models.media.diffusers;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "mediaDiffusers")
public class DiffusersMediaModel {

  @Id
  private String _id;
  private String parent_id;
  private String reviews_id;
  private long createdAt;
  private long updatedAt;
  public MediaContent mediaContent;

  public DiffusersMediaModel(){}

  public DiffusersMediaModel(DiffusersMediaModel dataModel){
    this.mediaContent = dataModel.mediaContent;
  }
  
  public static class MediaContent {
    public TitleContent titleContent;
    public List<List<Image>> images;

    public static class TitleContent {
      public String productLogo;
      public String descriptionVideo;
    }

    public static class Image {
      public String media;
      public String src;
    }
  }

  public void setId(String _id){
    this._id = _id;
  }

  public void setReviewsId(String reviews_id){
    this.reviews_id = reviews_id;
  }

  public String getId(){
    return this._id;
  }

  public void setParentId(String parent_id){
    this.parent_id = parent_id;
  }

  public String getParentId(){
    return this.parent_id;
  }

  public String getReviewsId(){
    return this.reviews_id;
  }

  public long getCreateTime(){
    return this.createdAt;
  }

  public void setCreateTime(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdateTime(){
    return this.updatedAt;
  }

  public void setUpdateTime(long newTime){
    this.updatedAt = newTime;
  }
}
