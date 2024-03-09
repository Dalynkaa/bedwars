package me.dalynkaa.spbedwars.huds.utils;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.Logger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CustomBossBar {
    private static final Map<UUID, CustomBossBar> PLAYER_BOSS_BARS = new HashMap<>();
    private final BossBar bossBar;
    private final Audience audience;
    private boolean visible;

    private CustomBossBar(UUID uuid) {
        bossBar = BossBar.bossBar(Component.text(""), 0F, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
        Player player = Bukkit.getPlayer(uuid);
        audience = BukkitAudiences.create(SPBedWars.getInstance()).player(player);
        if (player != null) {
            audience.showBossBar(bossBar);
            visible = true;
            PLAYER_BOSS_BARS.put(uuid, this);
        }
    }

    public CustomBossBar setTitle(Component title) {
        bossBar.name(title);
        return this;
    }

    public CustomBossBar setColor(BossBar.Color color) {
        bossBar.color(color);
        return this;
    }

    public CustomBossBar setStyle(BossBar.Overlay style) {
        bossBar.overlay(style);
        return this;
    }

    public CustomBossBar setVisible(boolean visible) {
        if (visible) {
            audience.showBossBar(bossBar);
            this.visible = true;
        } else {
            audience.hideBossBar(bossBar);
            this.visible = false;
        }
        return this;
    }

    public boolean isVisible() {
        return this.visible;
    }


    public static CustomBossBar get(UUID uuid) {
        Logger.debug("Getting boss bar for player " + PLAYER_BOSS_BARS.toString());
        if (PLAYER_BOSS_BARS.get(uuid) == null) {
            PLAYER_BOSS_BARS.put(uuid, new CustomBossBar(uuid));
        }
        return PLAYER_BOSS_BARS.get(uuid);
    }

    public static void remove(UUID uuid) {
        if (PLAYER_BOSS_BARS.get(uuid) != null) {
            PLAYER_BOSS_BARS.get(uuid).audience.hideBossBar(PLAYER_BOSS_BARS.get(uuid).bossBar);
            PLAYER_BOSS_BARS.remove(uuid);
        }
    }

}
