package com.server.databases.mongodb.dto.main;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record GetManyById(@NotEmpty List<String> id, @NotBlank @Pattern(regexp = ".*_.*", message = "The field must contain '_'") String collectionName){
  
  // @JsonProperty private List<String> id;
  // @JsonProperty @NotBlank @Pattern(regexp = ".*_.*", message = "The field must contain '_'") private String collectionName;

  // public List<String> getId() {
  //   return id;
  // }

  // public String getCollectionName(){
  //   return collectionName;
  // }

  // public void setId(List<String> id) {
  //   this.id = id;
  // }

  // public void setCollectionName(String collectionName) {
  //   this.collectionName = collectionName;
  // }

  // public GetManyById(){}

}
