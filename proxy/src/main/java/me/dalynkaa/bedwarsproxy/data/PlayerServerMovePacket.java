package me.dalynkaa.bedwarsproxy.data;

import com.google.gson.Gson;

import java.util.UUID;

public class PlayerServerMovePacket {
    UUID playerUUID;
    String serverName;

    public PlayerServerMovePacket(UUID playerUUID, String serverName) {
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
    public static PlayerServerMovePacket fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerServerMovePacket.class);
    }
}
