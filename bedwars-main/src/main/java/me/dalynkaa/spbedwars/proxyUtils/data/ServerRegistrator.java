package me.dalynkaa.spbedwars.proxyUtils.data;

import com.google.gson.Gson;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;

import java.util.UUID;

public class ServerRegistrator {
    private final UUID serverId;
    private final String serverName;
    private final ArenaTypes arenaType;
    private final ServerInfo serverInfo;
    private final Boolean isEdit;
    public ServerRegistrator(UUID serverId, String serverName, ArenaTypes arenaType, ServerInfo serverInfo, boolean isEdit) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.arenaType = arenaType;
        this.serverInfo = serverInfo;
        this.isEdit = isEdit;
    }

    public UUID getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public ArenaTypes getArenaType() {
        return arenaType;
    }
    public ServerInfo getServerInfo(){
        return serverInfo;
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public String toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    public static ServerRegistrator fromJson(String json){
        Gson gson = new Gson();
        ServerRegistrator serverRegistrator = gson.fromJson(json, ServerRegistrator.class);
        return serverRegistrator;
    }
}
