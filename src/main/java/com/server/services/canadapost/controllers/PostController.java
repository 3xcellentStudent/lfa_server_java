package com.server.services.canadapost.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.canadapost.config.PostConfig;

@RestController
@RequestMapping("/canada-post")
public class PostController {

  private PostConfig config = new PostConfig();

  @GetMapping("/availability")
  public void getAvailability(){
    
  }
}
