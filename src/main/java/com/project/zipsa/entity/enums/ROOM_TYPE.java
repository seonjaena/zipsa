package com.project.zipsa.entity.enums;

public enum ROOM_TYPE implements EnumFlagable<ROOM_TYPE> {

    APARTMENT('A', "APARTMENT"),
    ONE_ROOM('1', "ONE_ROOM"),
    TWO_ROOM('2', "TWO_ROOM"),
    THREE_ROOM('3', "THREE_ROOM"),
    FOUR_ROOM('4', "FOUR_ROOM"),
    MOTEL('M', "MOTEL"),
    HOTEL('H', "HOTEL"),
    ETC('E', "ETC");

    private final char flag;
    private final String text;

    private ROOM_TYPE(char flag, String text) {
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

    public static class Converter extends EnumConverter<ROOM_TYPE> {
        public Converter() {
            super(ROOM_TYPE.class);
        }
    }
}
