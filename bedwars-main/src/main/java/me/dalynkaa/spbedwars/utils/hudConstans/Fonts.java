package me.dalynkaa.spbedwars.utils.hudConstans;

import net.kyori.adventure.key.Key;

public enum Fonts {
    BED_STATUS("bed_status"),
    SPACE("offsets");

    private final String name;
    private final Key key;

    Fonts(String name) {
        this.name = name;
        this.key = Key.key("minecraft", name);
    }

    public Key getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
