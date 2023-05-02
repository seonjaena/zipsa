package com.project.zipsa.entity.enums;

public enum BOARD_LIKE_TYPE implements EnumFlagable<BOARD_LIKE_TYPE> {

    LIKE('L', "LIKE"),
    DISLIKE('D', "DISLIKE");

    private final char flag;
    private final String text;

    private BOARD_LIKE_TYPE(char flag, String text) {
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

    public static class Converter extends EnumConverter<BOARD_LIKE_TYPE> {
        public Converter() {
            super(BOARD_LIKE_TYPE.class);
        }
    }
}
