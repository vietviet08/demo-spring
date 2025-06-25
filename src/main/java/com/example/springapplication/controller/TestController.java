package com.example.springapplication.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapplication.model.Greeting;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = { "/test" }, produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
public class TestController {

  private final Environment environment;

  @GetMapping("/hello")
  public ResponseEntity<Greeting> test() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new Greeting(1L, "Hello, World!, vietquoc "));
  }

  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body("JAVA_HOME: " + environment.getProperty("JAVA_HOME"));
  }

}
