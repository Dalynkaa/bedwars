package me.dalynkaa.spbedwars.proxyUtils.data;

import com.google.gson.Gson;

public record ServerInfo(String serverIp, Integer serverPort) {


    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static ServerInfo fromJson(String json) {
        Gson gson = new Gson();
        ServerInfo serverRegistrator = gson.fromJson(json, ServerInfo.class);
        return serverRegistrator;
    }
}
