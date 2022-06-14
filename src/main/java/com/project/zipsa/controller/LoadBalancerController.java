package com.project.zipsa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/healthcheck")
public class LoadBalancerController {

    @GetMapping
    public HttpStatus get() {
        log.info("Load Balancing Health Check Success");
        return HttpStatus.OK;
    }

}
