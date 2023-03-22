package com.project.zipsa.validate;

import io.netty.util.internal.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegExValidator implements ConstraintValidator<RegExValidate, String> {

    private String message;
    private VALIDATE_REGEX regex;

    @Override
    public void initialize(RegExValidate constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtil.isNullOrEmpty(value)) {
            if(!regex.matches(value)) {
                addConstraintViolation(context, message);
                return false;
            }
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
        // 기본 메시지 비활성화
        context.disableDefaultConstraintViolation();
        // 새로운 메시지 추가
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
}
