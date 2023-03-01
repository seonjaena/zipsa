package com.project.zipsa.validate;

import io.netty.util.internal.StringUtil;

public enum VALIDATE_REGEX {

    NONE(""),
    USER_ID("^\\w+@\\w+\\.\\w+(\\.\\w+)?"),
    PASSWORD("^" +
            "(?=.{8,16}$)" +
            "(?=.*[0-9]+.*)" +
            "(?=.*[a-zA-Z]+.*)" +
            "(?=.*[!@#$%&^()*+-_+=~`]+.*)" +
            "[0-9a-zA-Z!@#$%&^()*+-_+=~`]+$");

    private String regex;

    VALIDATE_REGEX(String regex) { this.regex = regex; }

    public boolean matches(final String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return false;
        }
        return value.matches(regex);
    }

}
