package com.server.databases.mongodb.models.media;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.databases.mongodb.models.media.MediaModel.MediaContent.Image;
import com.server.databases.mongodb.models.media.MediaModel.MediaContent.TitleContent;

// @Component
// @Document(collection = "media_diffusers")
// public class DiffusersMediaModel {

//   @Id
//   private String id;
//   private String parentId;
//   private long createdAt;
//   private long updatedAt;
//   public TitleContent titleContent = new TitleContent();
//   public ArrayList<ArrayList<Image>> images = new ArrayList<>();


//   public DiffusersMediaModel(){}

//   public DiffusersMediaModel(DiffusersMediaModel dataModel){
//     this.titleContent = dataModel.titleContent;
//     this.images = dataModel.images;
//   }
  
//   public static class MediaContent {
//     public TitleContent titleContent;
//     public ArrayList<ArrayList<Image>> images;

//     public static class TitleContent {
//       public String productLogo;
//       public String descriptionVideo;
//     }

//     public static class Image {
//       public String media;
//       public String src;
//     }
//   }

//   public void setId(String id){
//     this.id = id;
//   }

//   public String getId(){
//     return this.id;
//   }

//   public void setParentId(String parentId){
//     this.parentId = parentId;
//   }

//   public String getParentId(){
//     return this.parentId;
//   }

//   public long getCreateAt(){
//     return this.createdAt;
//   }

//   public void setCreateAt(long newTime){
//     this.createdAt = newTime;
//   }

//   public long getUpdateAt(){
//     return this.updatedAt;
//   }

//   public void setUpdateAt(long newTime){
//     this.updatedAt = newTime;
//   }
// }


@Component
@Document(collection = "media_diffusers")
public class MediaModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty private String parentId;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;
  @JsonProperty private TitleContent titleContent = new TitleContent();
  @JsonProperty private ArrayList<ArrayList<Image>> images = new ArrayList<>();

  public MediaModel(){}

  public MediaModel(MediaModel dataModel){
    this.titleContent = dataModel.titleContent;
    this.images = dataModel.images;
  }

  public static class MediaContent {
    @JsonProperty private TitleContent titleContent;
    @JsonProperty private ArrayList<ArrayList<Image>> images;

    public static class TitleContent {
      @JsonProperty private String productLogo;
      @JsonProperty private String descriptionVideo;
    }

    public static class Image {
      @JsonProperty private String media;
      @JsonProperty private String src;
    }
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
}