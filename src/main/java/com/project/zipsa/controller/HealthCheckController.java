package com.project.zipsa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/healthcheck")
public class HealthCheckController {

    @GetMapping
    public String get() {
        return "OK";
    }

}
