package me.dalynkaa.bedwarslobby.events;

import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.MarkerData;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MarkerExitEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final MarkerData markerData;
    private final BPlayer player;
    private final World world;
    private final String rawMarkerData;

    public MarkerExitEvent(MarkerData markerData, BPlayer player, World world, String rawMarkerData) {
        this.markerData = markerData;
        this.player = player;
        this.world = world;
        this.rawMarkerData = rawMarkerData;
    }

    public MarkerData getMarkerData() {
        return markerData;
    }

    public BPlayer getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public String getRawMarkerData() {
        return rawMarkerData;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
