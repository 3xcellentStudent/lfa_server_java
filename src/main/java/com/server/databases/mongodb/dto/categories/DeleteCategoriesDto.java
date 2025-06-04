package com.server.databases.mongodb.dto.categories;

import java.util.List;

public class DeleteCategoriesDto {
  
  private String id;
  private String categoryName;
  private List<Integer> indexes;

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getCategoryName(){
    return categoryName;
  }

  public void setCategoryName(String categoryName){
    this.categoryName = categoryName;
  }

  public List<Integer> getIndexes(){
    return indexes;
  }

  public void setIndexes(List<Integer> indexes){
    this.indexes = indexes;
  }

  public DeleteCategoriesDto(){}

  public DeleteCategoriesDto(String id, String categoryName, List<Integer> indexes){
    this.id = id;
    this.categoryName = categoryName;
    this.indexes = indexes;
  }

}
