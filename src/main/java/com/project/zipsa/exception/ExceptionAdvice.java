package com.project.zipsa.exception;

import com.project.zipsa.exception.custom.UnAuthenticatedException;
import com.project.zipsa.exception.custom.UnAuthorizedException;
import com.project.zipsa.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(UnAuthenticatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String unAuthenticatedException(UnAuthenticatedException e) {
        log.error("message = {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String unAuthorizedException(UnAuthorizedException e) {
        log.error("message = {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotFoundException(UserNotFoundException e) {
        log.error("message = {}", e.getMessage());
        return e.getMessage();
    }

}
