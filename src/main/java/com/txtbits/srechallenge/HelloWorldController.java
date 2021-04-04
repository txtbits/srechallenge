package com.txtbits.srechallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWordController {

    private static final String template = "Hello World from %s!";

    @Value("${application.env}")
    private String env;

    @GetMapping("/hello")
    public ResponseEntity<Object> getHello() {
        String response = String.format(template, env);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}