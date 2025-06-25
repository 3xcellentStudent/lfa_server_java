package com.server.databases.mongodb.models.categories;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class CategoriesModel {
  @Id
  @JsonProperty private String id;
  @JsonProperty private String categoryName;
  @JsonProperty private ArrayList<String> categoryIdArray;

  public String getId() {
    return id;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public ArrayList<String> getCategoryIdArray() {
    return categoryIdArray;
  }

  public CategoriesModel(){}

  public CategoriesModel(CategoriesModel data) {
    this.categoryName = data.categoryName;
    this.categoryIdArray = data.categoryIdArray;
  }
  
}
