package me.dalynkaa.spbedwars.huds;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.huds.actionBarHuds.ActionBarRenderer;
import me.dalynkaa.spbedwars.huds.bossBarHuds.BossBarRenderer;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class MainHudRenderer {

    private static final List<HudsPlayer> hudsPlayerList = new ArrayList<>();
    private static int renderKey = 0;

    private MainHudRenderer() {
        startRenderer();
    }

    public static void startRenderer() {
        if (renderKey == 0) {
            Logger.debug("Starting MainHudRenderer");
            renderKey = Bukkit.getScheduler().runTaskTimerAsynchronously(SPBedWars.getInstance(), MainHudRenderer::render, 0, 1).getTaskId();
        }
    }

    public static void initializePlayer(BPlayer player) {
        Logger.debug("Hud initializing player " + player.getPlayer().getName() + " with UUID " + player.getUuid());
        HudsPlayer hudsPlayer = new HudsPlayer(player);
        hudsPlayerList.add(hudsPlayer);
        Logger.debug("Players hud list size: " + hudsPlayerList.size());

    }

    public static void removePlayer(BPlayer player) {
        HudsPlayer hudsPlayer1 = hudsPlayerList.stream().filter(hudsPlayer2 -> hudsPlayer2.getPlayer().getUuid().equals(player.getUuid())).findFirst().orElse(null);
        if (hudsPlayer1 != null) {
            hudsPlayer1.getActionBarRenderer().removePlayer(player.getUuid());
            hudsPlayer1.getBossBarRenderer().removePlayer(player.getUuid());
        }
        hudsPlayerList.removeIf(hudsPlayer -> hudsPlayer.getPlayer().getUuid().equals(player.getUuid()));
    }

    public static void clearHuds() {
        if (renderKey != 0) {
            Bukkit.getScheduler().cancelTask(renderKey);
            renderKey = 0;
        }
    }

    private static void render() {
        for (HudsPlayer hudsPlayer : hudsPlayerList) {
            //Logger.debug("Rendering huds for player " + hudsPlayer.getPlayer().getPlayer().getName() + " with UUID " + hudsPlayer.getPlayer().getUuid());
            hudsPlayer.getActionBarRenderer().render();
            hudsPlayer.getBossBarRenderer().render();
        }
    }
}
