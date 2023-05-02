package com.project.zipsa.entity.enums;

public enum BOARD_TYPE implements EnumFlagable<BOARD_TYPE> {

    NOTICE('N', "NOTICE"),
    GENERAL('G', "NOTICE"),
    VOTE('V', "VOTE");

    private final char flag;
    private final String text;

    private BOARD_TYPE(char flag, String text) {
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

    public static class Converter extends EnumConverter<BOARD_TYPE> {
        public Converter() {
            super(BOARD_TYPE.class);
        }
    }
}
