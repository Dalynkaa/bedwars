package me.dalynkaa.spbedwars.proxyUtils.data;

import com.google.gson.Gson;

import java.util.UUID;

public class ServerEditRegistrator {
    private UUID serverId;
    private boolean isEdit;

    public ServerEditRegistrator(UUID serverId, boolean isEdit) {
        this.serverId = serverId;
        this.isEdit = isEdit;
    }

    public UUID getServerId() {
        return serverId;
    }

    public boolean isEdit() {
        return isEdit;
    }
    public String toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    public static ServerEditRegistrator fromJson(String json){
        Gson gson = new Gson();
        ServerEditRegistrator serverEditRegistrator = gson.fromJson(json, ServerEditRegistrator.class);
        return serverEditRegistrator;
    }
}
