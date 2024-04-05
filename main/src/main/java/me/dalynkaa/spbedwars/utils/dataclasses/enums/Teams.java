package me.dalynkaa.spbedwars.utils.dataclasses.enums;

import me.dalynkaa.spbedwars.huds.utils.hudConstans.StatusChars;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;


public enum Teams {
    RED(TextColor.fromCSSHexString("#d63031"), TextColor.fromCSSHexString("#ff7675"), "Красная", "red", DyeColor.RED, "▃", StatusChars.BED_RED, "", true),
    BLUE(TextColor.fromCSSHexString("#0984e3"), TextColor.fromCSSHexString("#74b9ff"), "Синяя", "blue", DyeColor.BLUE, "▚", StatusChars.BED_BLUE, "", true),
    GREEN(TextColor.fromCSSHexString("#00b894"), TextColor.fromCSSHexString("#55efc4"), "Зеленая", "green", DyeColor.LIME, "▘", StatusChars.BED_GREEN, "", true),
    YELLOW(TextColor.fromCSSHexString("#fdcb6e"), TextColor.fromCSSHexString("#ffeaa7"), "Желтая", "yellow", DyeColor.YELLOW, "▟", StatusChars.BED_YELLOW, "", true),
    // SOLO/DUO
    AQUA(TextColor.fromCSSHexString("#00cec9"), TextColor.fromCSSHexString("#00cec9"), "Бирюзовая", "aqua", DyeColor.CYAN, "▜", StatusChars.BED_YELLOW, "", false),
    WHITE(TextColor.fromCSSHexString("#dfe6e9"), TextColor.fromCSSHexString("#dfe6e9"), "Белая", "white", DyeColor.WHITE, "▛", StatusChars.BED_YELLOW, "", false),
    PINK(TextColor.fromCSSHexString("#fab1a0"), TextColor.fromCSSHexString("#fd79a8"), "Розовая", "pink", DyeColor.PINK, "▙", StatusChars.BED_YELLOW, "", false),
    GRAY(TextColor.fromCSSHexString("#636e72"), TextColor.fromCSSHexString("#b2bec3"), "Серая", "gray", DyeColor.GRAY, "▚", StatusChars.BED_YELLOW, "", false),
    ;

    private final TextColor teamCollor;
    private final TextColor secondCollor;
    private final String name;
    private final String id;
    private final DyeColor dyeColor;
    private final String symvol;
    private final StatusChars bedIcon;
    private final String playerIcon;
    private final boolean fourTeam;

    Teams(TextColor teamCollor, TextColor secondCollor, String name, String id, DyeColor dyeColor, String symvol, StatusChars bedIcon, String playerIcon, boolean fourTeam) {
        this.teamCollor = teamCollor;
        this.secondCollor = secondCollor;
        this.name = name;
        this.id = id;
        this.dyeColor = dyeColor;
        this.symvol = symvol;
        this.bedIcon = bedIcon;
        this.playerIcon = playerIcon;
        this.fourTeam = fourTeam;
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

    public StatusChars getBedIcon() {
        return bedIcon;
    }

    public String getPlayerIcon() {
        return playerIcon;
    }

    public boolean isFourTeam() {
        return fourTeam;
    }
}
