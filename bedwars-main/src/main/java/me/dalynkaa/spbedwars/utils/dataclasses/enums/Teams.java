package me.dalynkaa.spbedwars.utils.dataclasses.enums;

import me.dalynkaa.spbedwars.utils.hudConstans.Chars;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;


public enum Teams {
    RED(TextColor.fromCSSHexString("#d63031"), TextColor.fromCSSHexString("#ff7675"), "Красная", "red", DyeColor.RED, "▃", Chars.BED_RED, ""),
    BLUE(TextColor.fromCSSHexString("#0984e3"), TextColor.fromCSSHexString("#74b9ff"), "Синяя", "blue", DyeColor.BLUE, "▚", Chars.BED_BLUE, ""),
    GREEN(TextColor.fromCSSHexString("#00b894"), TextColor.fromCSSHexString("#55efc4"), "Зеленая", "green", DyeColor.LIME, "▘", Chars.BED_GREEN, ""),
    YELLOW(TextColor.fromCSSHexString("#fdcb6e"), TextColor.fromCSSHexString("#ffeaa7"), "Желтая", "yellow", DyeColor.YELLOW, "▟", Chars.BED_YELLOW, ""),
    ;

    private final TextColor teamCollor;
    private final TextColor secondCollor;
    private final String name;
    private final String id;
    private final DyeColor dyeColor;
    private final String symvol;
    private final Chars bedIcon;
    private final String playerIcon;

    Teams(TextColor teamCollor, TextColor secondCollor, String name, String id, DyeColor dyeColor, String symvol, Chars bedIcon, String playerIcon) {
        this.teamCollor = teamCollor;
        this.secondCollor = secondCollor;
        this.name = name;
        this.id = id;
        this.dyeColor = dyeColor;
        this.symvol = symvol;
        this.bedIcon = bedIcon;
        this.playerIcon = playerIcon;
    }

    public TextColor getTeamCollor() {
        return teamCollor;
    }

    public TextColor getSecondCollor() {
        return secondCollor;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Material getWoolMaterial() {
        return Material.valueOf(dyeColor.name() + "_WOOL");
    }

    public String getSymvol() {
        return symvol;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public Chars getBedIcon() {
        return bedIcon;
    }

    public String getPlayerIcon() {
        return playerIcon;
    }
}
