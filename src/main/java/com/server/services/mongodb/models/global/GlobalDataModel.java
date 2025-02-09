package com.server.services.mongodb.models.global;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "global_data")
public class GlobalDataModel {

  @Id
  private String id;
  public Colors colors;
  public String shortDescription;
  private long createdAt;
  private long updatedAt;

  public GlobalDataModel(){}

  public GlobalDataModel(GlobalDataModel globalDataModel){
    this.colors = globalDataModel.colors;
  }

  public static class Colors {
    public TextColors text;
    public BackgroundColors backgrounds;

    public static class TextColors {
      public ColorTypes primary;
      public ColorTypes secondary;
      public ColorTypes optional;
    }
    public static class BackgroundColors {
      public ColorTypes primary;
      public ColorTypes secondary;
      public ColorTypes optional;
      public ColorTypes elementsPrimary;
      public ColorTypes elementsSecondary;
      public ColorTypes elementsOptional;
    }
    public static class ColorTypes {
      public String hex;
      public String rgb;
    }
  }

  public void setId(String newId){
    this.id = newId;
  }

  public String getId(){
    return this.id;
  }

  public void setCreatedAt(long timestamp){
    this.createdAt = timestamp;
  }

  public long getCreatedAt(){
    return this.createdAt;
  }

  public void setUpdatedAt(long timestamp){
    this.updatedAt = timestamp;
  }

  public long getUpdatedAt(){
    return this.updatedAt;
  }

}
