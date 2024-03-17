package me.dalynkaa.spbedwars.utils.dataclasses.enums;

public enum ServerType {
    CLASSIC("Классический"),
    RANDOM("Случайный");

    private final String translate;

    ServerType(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }
}
