package com.server.databases.mongodb.dto.main;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record DeleteManyById(@NotEmpty List<String> ids, @NotBlank String parentId){}
