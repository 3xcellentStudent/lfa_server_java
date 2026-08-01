package com.server.databases.mongodb.dto.main;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record DeleteManyFromArrayDto(@NotEmpty List<Integer> indexes, @NotBlank String selector, @NotBlank String id){
  
}
