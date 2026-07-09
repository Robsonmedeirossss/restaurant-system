package com.dev.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenAPISwaggerController {
  

  @GetMapping("/")
  public String swaggerRedirect() {
    return "redirect:/swagger-ui/index.html";
  }

}
