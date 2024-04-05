package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.game;

import com.google.gson.Gson;

import java.util.UUID;

public class GameDeleteRegistrator {
    private final UUID serverId;
    private final UUID gameId;

    public GameDeleteRegistrator(UUID serverId, UUID gameId) {
        this.serverId = serverId;
        this.gameId = gameId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static GameDeleteRegistrator fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameDeleteRegistrator.class);
    }

}
