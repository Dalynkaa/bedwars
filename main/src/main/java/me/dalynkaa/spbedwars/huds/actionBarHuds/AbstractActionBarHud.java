package me.dalynkaa.spbedwars.huds.actionBarHuds;

import me.dalynkaa.spbedwars.huds.utils.RenderStatus;

import java.util.UUID;

public abstract class AbstractActionBarHud {

    public abstract String getName();

    public abstract RenderStatus render(UUID playerUUID);

}
