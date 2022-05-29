package com.project.zipsa.controller;

import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entryPoint")
    public void entryPointException() {
        throw new UnAuthenticatedException("인증에 실패했습니다.");
    }

    @GetMapping(value = "/accessDenied")
    public void accessDeniedException() {
        throw new UnAuthorizedException("현재 권한으로 접근할 수 없습니다.");
    }

}
