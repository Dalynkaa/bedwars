package me.dalynkaa.spbedwars.proxyUtils.data;

import com.google.gson.Gson;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;

import java.util.UUID;

public class ArenaRegistrator {
    private final UUID serverId;
    private final UUID arenaId;
    private final ArenaTypes arenaType;
    private final String arenaName;

    public ArenaRegistrator(UUID serverId, ArenaTypes arenaType, String arenaName, UUID arenaId) {
        this.serverId = serverId;
        this.arenaType = arenaType;
        this.arenaName = arenaName;
        this.arenaId = arenaId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public ArenaTypes getArenaType() {
        return arenaType;
    }

    public String getArenaName() {
        return arenaName;
    }

    public UUID getArenaId() {
        return arenaId;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static ServerRegistrator fromJson(String json) {
        Gson gson = new Gson();
        ServerRegistrator serverRegistrator = gson.fromJson(json, ServerRegistrator.class);
        return serverRegistrator;
    }
}
