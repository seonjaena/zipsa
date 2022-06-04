package com.project.zipsa.controller;

import com.project.zipsa.entity.Test;
import com.project.zipsa.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(value = "/test/test")
    public String a(Principal principal) {
        log.error("data = {}", principal.getName());
        testService.createTest(new Test("1", LocalDateTime.now().toString()));
        return "ok";
    }

}
