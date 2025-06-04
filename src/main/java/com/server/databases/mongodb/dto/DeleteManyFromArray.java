package com.server.databases.mongodb.dto;

import java.util.List;

public class DeleteManyFromArray {
  private List<Integer> indexes;
  private String selector;
  private String id;

  DeleteManyFromArray(){}

  DeleteManyFromArray(DeleteManyFromArray requestBody){
    this.indexes = requestBody.indexes;
    this.selector = requestBody.selector;
    this.id = requestBody.id;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Integer> getIndexes(){
    return this.indexes;
  }

  public void setIndexes(List<Integer> indexes){
    this.indexes = indexes;
  }

  public String getSelector(){
    return this.selector;
  }

  public void setSelector(String selector){
    this.selector = selector;
  }

}
