package me.dalynkaa.spbedwars.huds.utils.hudConstans;

import net.kyori.adventure.key.Key;

public enum Fonts {
    BED_STATUS("bed_status"),
    BED_STATUS_NEW("bed_status_new"),
    HUD("hud"),
    GAME_WAITING("game_waiting"),
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
