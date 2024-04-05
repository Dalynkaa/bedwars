package me.dalynkaa.spbedwars.utils.dataclasses.enums;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public enum ItemSpawner {
    IRON(1, Material.IRON_INGOT, Material.IRON_BLOCK, "Железо", TextColor.fromCSSHexString("#b2bec3"), TextColor.fromCSSHexString("#dfe6e9")),
    GOLD(5, Material.GOLD_INGOT, Material.GOLD_BLOCK, "Золото", TextColor.fromCSSHexString("#fdcb6e"), TextColor.fromCSSHexString("#ffeaa7")),
    DIAMOND(30, Material.DIAMOND, Material.DIAMOND_BLOCK, "Алмазы", TextColor.fromCSSHexString("#00cec9"), TextColor.fromCSSHexString("#81ecec")),
    EMERALD(60, Material.EMERALD, Material.EMERALD_BLOCK, "Изумруды", TextColor.fromCSSHexString("#00b894"), TextColor.fromCSSHexString("#55efc4"));

    private final Integer spawnCoolDown;
    private final Material item;
    private final Material hologram;
    private final String name;
    private final TextColor mainCollor;
    private final TextColor secondCollor;

    ItemSpawner(Integer spawnCoolDown, Material item, Material hologram, String name, TextColor mainCollor, TextColor secondCollor) {
        this.spawnCoolDown = spawnCoolDown;
        this.item = item;
        this.hologram = hologram;
        this.name = name;
        this.mainCollor = mainCollor;
        this.secondCollor = secondCollor;
    }

    public Integer getSpawnCoolDown(Integer lvl) {
        return switch (lvl) {
            case 2 -> spawnCoolDown / 2;
            case 3 -> spawnCoolDown / 3;
            case 4 -> spawnCoolDown / 4;
            default -> spawnCoolDown;
        };
    }

    public Material getItem() {
        return item;
    }

    public Material getHologram() {
        return hologram;
    }

    public String getName() {
        return name;
    }

    public TextColor getMainCollor() {
        return mainCollor;
    }

    public TextColor getSecondCollor() {
        return secondCollor;
    }
}
