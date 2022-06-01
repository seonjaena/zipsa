package com.project.zipsa.controller;

import com.project.zipsa.entity.Test;
import com.project.zipsa.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(value = "/test/test")
    public String a() {
        testService.createTest(new Test("1", LocalDateTime.now().toString()));
        return "ok";
    }

}
