package com.project.zipsa.exception;

import com.project.zipsa.dto.GeneralResponseDto;
import com.project.zipsa.dto.enums.GENERAL_STATUS_ENUM;
import com.project.zipsa.exception.custom.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(UnAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> unAuthenticatedException(UnAuthenticatedException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(SendSMSFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> sendSMSFailException(SendSMSFailException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(DuplicateUserPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> duplicateUserPhoneException(DuplicateUserPhoneException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> unAuthorizedException(UnAuthorizedException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(BadUserPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> badUserPhoneException(BadUserPhoneException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(BadValidDeviceCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> badValidDeviceCodeException(BadValidDeviceCodeException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> userNotFoundException(UserNotFoundException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(UserIdNotSameWithTokenKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> userIdNotSameWithTokenKey(UserIdNotSameWithTokenKeyException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(DeleteMyInfoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> deleteMyInfoException(DeleteMyInfoException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> refreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(RefreshTokenExpireException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> refreshTokenExpireException(RefreshTokenExpireException e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponseDto<GENERAL_STATUS_ENUM, String> unknownException(Exception e) {
        log.error("message = {}", e.getMessage());
        return new GeneralResponseDto<>(GENERAL_STATUS_ENUM.FAIL, messageSource.getMessage("error.system.unknown", null, Locale.KOREA));
    }

}
