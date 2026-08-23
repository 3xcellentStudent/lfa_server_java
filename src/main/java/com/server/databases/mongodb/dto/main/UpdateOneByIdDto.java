package com.server.databases.mongodb.dto.main;


import jakarta.validation.constraints.NotBlank;

public record UpdateOneByIdDto(@NotBlank String id, @NotBlank String path, Object data){}
