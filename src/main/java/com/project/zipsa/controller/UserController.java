package com.project.zipsa.controller;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.auth.LoginRequestDto;
import com.project.zipsa.dto.auth.ResponseLoginDto;
import com.project.zipsa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public GeneralResponseDto<ResponseLoginDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new GeneralResponseDto<>(userService.login(loginRequestDto.getUserId(), loginRequestDto.getUserPw()));
    }

    @PostMapping(value = "/join")
    public String join() {
        return null;
    }

}
