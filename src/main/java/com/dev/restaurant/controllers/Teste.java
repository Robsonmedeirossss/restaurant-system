package com.dev.restaurant.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Teste {

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public String hello() {
    return "oi";
  }
  
}
