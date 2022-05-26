package com.project.zipsa.entity.enums;

public enum ROOM_CONTRACT_TYPE implements EnumFlagable<ROOM_CONTRACT_TYPE> {

    MONTH_RENT('M', "MONTH_RENT"),
    YEAR_RENT('Y', "YEAR_RENT");

    private final char flag;
    private final String text;

    private ROOM_CONTRACT_TYPE(char flag, String text) {
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

    public static class Converter extends EnumConverter<ROOM_CONTRACT_TYPE> {
        public Converter() {
            super(ROOM_CONTRACT_TYPE.class);
        }
    }
}
