package me.dalynkaa.spbedwars.utils.dataclasses.game.enums;

import java.util.List;

public enum GameShopType {
    NORMAL,
    UPGRADE;

    public static List<String> getNames(){
        return List.of("NORMAL", "UPGRADE");
    }
}
