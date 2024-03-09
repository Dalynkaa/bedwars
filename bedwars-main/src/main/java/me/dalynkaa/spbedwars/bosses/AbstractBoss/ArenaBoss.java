package me.dalynkaa.spbedwars.bosses.AbstractBoss;

import me.dalynkaa.spbedwars.utils.dataclasses.game.GameLocation;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;

public abstract class ArenaBoss {
    public abstract String getName();
    public abstract Double getHealth();
    public abstract Boolean isSummoned();
    public abstract void move(GameLocation destination, boolean withAnimation);
    public abstract void summon(GameLocation location);
}
