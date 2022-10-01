package com.project.zipsa.controller;

import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_STATUS;
import com.project.zipsa.repository.TestRepository;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/test")
public class TestController {

    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @GetMapping(value = "/test")
    public void test(@RequestParam(name = "targetId") String targetId,
                     @RequestParam(name = "title") String title,
                     @RequestParam(name = "body") String body) {
        Users user = userRepository.findByUserIdAndUserStatus(targetId, USER_STATUS.NORMAL).get();
        String token = user.getUserToken();
        testRepository.test(targetId, title, body, token);
    }

}
