package com.project.zipsa.entity.enums;

public enum CHECK_CODE_TYPE implements EnumFlagable<CHECK_CODE_TYPE> {

    EMAIL('E', "EMAIL"),
    PHONE('P', "PHONE");

    private final char flag;
    private final String text;

    private CHECK_CODE_TYPE(char flag, String text) {
        this.flag = flag;
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public Character get() {
        return this.flag;
    }

    public static class Converter extends EnumConverter<CHECK_CODE_TYPE> {
        public Converter() {
            super(CHECK_CODE_TYPE.class);
        }
    }

}