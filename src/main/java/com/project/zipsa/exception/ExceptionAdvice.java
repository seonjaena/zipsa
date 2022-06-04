package com.project.zipsa.exception;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.dto.enums.GENERAL_FAIL_DETAIL;
import com.project.zipsa.exception.custom.*;
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
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> unAuthenticatedException(UnAuthenticatedException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.WRONG_USER_INFO);
    }

    @ExceptionHandler(SendSMSFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> sendSMSFailException(SendSMSFailException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.SEND_SMS);
    }

    @ExceptionHandler(DuplicateUserPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> duplicateUserPhoneException(DuplicateUserPhoneException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.DUPLICATE_PHONE);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> unAuthorizedException(UnAuthorizedException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.WRONG_AUTHORITY);
    }

    @ExceptionHandler(BadUserPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> badUserPhoneException(BadUserPhoneException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.BAD_TYPE_PHONE);
    }

    @ExceptionHandler(BadValidDeviceCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> badValidDeviceCodeException(BadValidDeviceCodeException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.BAD_DEVICE_CODE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userNotFoundException(UserNotFoundException e) {
        log.error("message = {}", e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, GENERAL_FAIL_DETAIL> unknownException(Exception e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, GENERAL_FAIL_DETAIL.UNKNOWN);
    }

}
