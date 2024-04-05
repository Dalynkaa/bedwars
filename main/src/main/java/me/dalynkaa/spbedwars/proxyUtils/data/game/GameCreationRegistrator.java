package me.dalynkaa.spbedwars.proxyUtils.data.game;

import com.google.gson.Gson;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;

import java.util.UUID;

public class GameCreationRegistrator {
    private final UUID serverId;
    private final UUID arenaId;
    private final ArenaTypes arenaType;
    private final UUID requestedPlayer;

    public GameCreationRegistrator(UUID serverId, ArenaTypes arenaType, UUID requestedPlayer, UUID arenaId) {
        this.serverId = serverId;
        this.arenaType = arenaType;
        this.requestedPlayer = requestedPlayer;
        this.arenaId = arenaId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public ArenaTypes getArenaType() {
        return arenaType;
    }

    public UUID getRequestedPlayer() {
        return requestedPlayer;
    }

    public UUID getArenaId() {
        return arenaId;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static GameCreationRegistrator fromJson(String json) {
        Gson gson = new Gson();
        GameCreationRegistrator serverRegistrator = gson.fromJson(json, GameCreationRegistrator.class);
        return serverRegistrator;
    }

}
