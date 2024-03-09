package me.dalynkaa.spbedwars.huds.bossBarHuds;

import me.dalynkaa.spbedwars.huds.utils.RenderStatus;

import java.util.UUID;

public abstract class AbstractBossBarHud {
    public abstract String getName();

    public abstract RenderStatus render(UUID player);
}
