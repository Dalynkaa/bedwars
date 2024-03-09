package me.dalynkaa.spbedwars.huds;

import me.dalynkaa.spbedwars.huds.actionBarHuds.ActionBarRenderer;
import me.dalynkaa.spbedwars.huds.bossBarHuds.BossBarRenderer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;


public class HudsPlayer {
    private final ActionBarRenderer actionBarRenderer;
    private final BossBarRenderer bossBarRenderer;
    private BPlayer player;

    public HudsPlayer(BPlayer player) {
        this.actionBarRenderer = new ActionBarRenderer();
        this.bossBarRenderer = new BossBarRenderer();
        this.player = player;
        initializePlayer();

    }

    private void initializePlayer() {
        actionBarRenderer.initializePlayer(player.getUuid());
        bossBarRenderer.initializePlayer(player.getUuid());
    }

    public ActionBarRenderer getActionBarRenderer() {
        return actionBarRenderer;
    }

    public BossBarRenderer getBossBarRenderer() {
        return bossBarRenderer;
    }


    public BPlayer getPlayer() {
        return player;
    }

    public void setPlayer(BPlayer player) {
        this.player = player;
    }
}
