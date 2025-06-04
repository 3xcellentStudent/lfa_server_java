package com.server.databases.mongodb.models.global;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@Document(collection = "global_data")
public class GlobalDataModel {

  @Id
  @JsonProperty private String id;
  @JsonProperty private Colors colors;
  @JsonProperty private Categories categories = new Categories();
  @JsonProperty private String type;
  @JsonProperty private long createdAt;
  @JsonProperty private long updatedAt;

  public GlobalDataModel(){}

  public GlobalDataModel(GlobalDataModel requestBody){
    this.colors = requestBody.colors;
    this.type = requestBody.type;
    this.categories = requestBody.categories;
  }

  public static class Colors {
    @JsonProperty private TextColors text;
    @JsonProperty private BackgroundColors backgrounds;

    public static class TextColors {
      @JsonProperty private ColorCode primaryText;
      @JsonProperty private ColorCode secondaryText;
      @JsonProperty private ColorCode optionalText;
    }

    public static class BackgroundColors {
      @JsonProperty private ColorCode primaryBg;
      @JsonProperty private ColorCode secondaryBg;
      @JsonProperty private ColorCode optionalBg;
      @JsonProperty private ColorCode elementsPrimaryBg;
      @JsonProperty private ColorCode elementsSecondaryBg;
      @JsonProperty private ColorCode elementsOptionalBg;
    }

    public static class ColorCode {
      @JsonProperty private String hex;
      @JsonProperty private String rgb;
    }
  }

  public static class Categories {
    @JsonProperty private Category diffusers = new Category();
    @JsonProperty private Category essentialOils = new Category();
    @JsonProperty private Category soaps = new Category();
    @JsonProperty private Category liquidSoaps = new Category();
    @JsonProperty private Category bathBombs = new Category();
    @JsonProperty private Category creams = new Category();

    // create gertters
    public Category getDiffusers(){
      return this.diffusers;
    }

    public Category getEssentialOils(){
      return this.essentialOils;
    }

    public Category getSoaps(){
      return this.soaps;
    }

    public Category getLiquidSoaps(){
      return this.liquidSoaps;
    }

    public Category getBathBombs(){
      return this.bathBombs;
    }

    public Category getCreams(){
      return this.creams;
    }

  }

  public static class Category {
    @JsonProperty private String name;
    @JsonProperty private String titleImage;
    @JsonProperty private String description;
    @JsonProperty private List<String> products = new ArrayList<>();
  }

  public String getId(){
    return this.id;
  }

  public void setId(String id){
    if(id != null || id.length() > 10){
      this.id = id;
    }
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

  public Categories getCategories(){
    return this.categories;
  }

  public String getType(){
    return this.type;
  }

}
