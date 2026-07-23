package com.server.databases.mongodb.dto.main;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record GetManyByNullableId(List<String> id, @NotBlank @Pattern(regexp = ".*_.*", message = "The field must contain '_'") String collectionName){
}