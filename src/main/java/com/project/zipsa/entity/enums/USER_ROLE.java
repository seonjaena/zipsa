package com.project.zipsa.entity.enums;

public enum USER_ROLE implements EnumFlagable<USER_ROLE> {

    ADMIN('A', "ROLE_ADMIN"),
    USER('U', "ROLE_USER");

    private final char flag;
    private final String text;

    private USER_ROLE(char flag, String text) {
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

    public static class Converter extends EnumConverter<USER_ROLE> {
        public Converter() {
            super(USER_ROLE.class);
        }
    }
}
