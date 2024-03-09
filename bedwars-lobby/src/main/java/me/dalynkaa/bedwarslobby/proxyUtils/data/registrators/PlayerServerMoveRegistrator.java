package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;

import java.util.UUID;

public class PlayerServerMoveRegistrator {
    UUID playerUUID;
    String serverName;

    public PlayerServerMoveRegistrator(UUID playerUUID, String serverName) {
        this.playerUUID = playerUUID;
        this.serverName = serverName;
    }
    public UUID getPlayerUUID() {
        return playerUUID;
    }
    public String getServerName() {
        return serverName;
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static PlayerServerMoveRegistrator fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerServerMoveRegistrator.class);
    }
}
