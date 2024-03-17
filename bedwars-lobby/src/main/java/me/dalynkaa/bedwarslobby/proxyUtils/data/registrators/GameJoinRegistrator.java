package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;

import java.util.UUID;

public class GameJoinRegistrator {
    private UUID userUUID;
    private UUID gameId;
    private UUID serverId;
    private JoinType joinType;

    public GameJoinRegistrator(UUID userUUID, UUID gameId, UUID serverId, JoinType joinType) {
        this.userUUID = userUUID;
        this.gameId = gameId;
        this.serverId = serverId;
        this.joinType = joinType;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GameJoinRegistrator fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameJoinRegistrator.class);
    }

    public enum JoinType {
        JOIN, SPEC, CREATE
    }
}
