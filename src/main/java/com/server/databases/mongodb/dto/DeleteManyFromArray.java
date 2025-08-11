package com.server.databases.mongodb.dto;

import java.util.List;

public class DeleteManyFromArray {
  private List<Integer> indexes;
  private String selector;
  private String id;
  private String collectionName;

  DeleteManyFromArray(){}

  DeleteManyFromArray(DeleteManyFromArray body){
    this.indexes = body.indexes;
    this.selector = body.selector;
    this.id = body.id;
    this.collectionName = body.collectionName;
  }

  DeleteManyFromArray(List<Integer> indexes, String selector, String id, String collectionName){
    this.indexes = indexes;
    this.selector = selector;
    this.id = id;
    this.collectionName = collectionName;
  }

  public String getId() {
    return this.id;
  }

  public List<Integer> getIndexes(){
    return this.indexes;
  }

  public String getSelector(){
    return this.selector;
  }

  public String getCollectionName(){
    return this.collectionName;
  }

}
