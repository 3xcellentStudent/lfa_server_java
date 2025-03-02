package com.server.databases.mongodb.helpers.bodies;

import java.util.List;

public class DeleteManyById {
  private List<String> id;
  private String parentId;

  DeleteManyById(){}

  DeleteManyById(DeleteManyById requestBody){
    this.id = requestBody.id;
    this.parentId = requestBody.parentId;
  }

  DeleteManyById(List<String> id, String parentId){
    this.id = id;
    this.parentId = parentId;
  }

  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
}
