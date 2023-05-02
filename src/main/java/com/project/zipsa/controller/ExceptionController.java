package com.project.zipsa.controller;

import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping(value = "/exception")
@RequiredArgsConstructor
public class ExceptionController {

    private final MessageSource messageSource;

    @GetMapping(value = "/entryPoint")
    public void entryPointException() {
        throw new UnAuthenticatedException(messageSource.getMessage("error.jwt.access.expire", null, Locale.KOREA));
    }

    @GetMapping(value = "/accessDenied")
    public void accessDeniedException() {
        throw new UnAuthorizedException(messageSource.getMessage("error.user.authority", null, Locale.KOREA));
    }

}
