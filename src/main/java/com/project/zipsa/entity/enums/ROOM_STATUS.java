package com.project.zipsa.entity.enums;

public enum ROOM_STATUS implements EnumFlagable<ROOM_STATUS> {

    NORMAL('N', "NORMAL"),
    EMPTY('E', "EMPTY");

    private final char flag;
    private final String text;

    private ROOM_STATUS(char flag, String text) {
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

    public static class Converter extends EnumConverter<ROOM_STATUS> {
        public Converter() {
            super(ROOM_STATUS.class);
        }
    }
}
