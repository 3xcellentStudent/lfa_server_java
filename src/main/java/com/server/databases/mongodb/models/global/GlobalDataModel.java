package com.server.databases.mongodb.models.global;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "global_data")
public class GlobalDataModel {

  @Id
  private String id;
  public Colors colors;
  public String type;
  private long createdAt;
  private long updatedAt;

  public GlobalDataModel(){}

  public GlobalDataModel(GlobalDataModel requestBody){
    this.colors = requestBody.colors;
    this.type = requestBody.type;
  }

  public static class Colors {
    public TextColors text;
    public BackgroundColors backgrounds;

    public static class TextColors {
      public ColorCode primaryText;
      public ColorCode secondaryText;
      public ColorCode optionalText;
    }

    public static class BackgroundColors {
      public ColorCode primaryBg;
      public ColorCode secondaryBg;
      public ColorCode optionalBg;
      public ColorCode elementsPrimaryBg;
      public ColorCode elementsSecondaryBg;
      public ColorCode elementsOptionalBg;
    }

    public static class ColorCode {
      public String hex;
      public String rgb;
    }
  }

  public void setId(String id){
    this.id = id;
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
