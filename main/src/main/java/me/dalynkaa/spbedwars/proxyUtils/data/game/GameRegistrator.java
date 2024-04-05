package me.dalynkaa.spbedwars.proxyUtils.data.game;

import com.google.gson.Gson;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;

import java.util.List;
import java.util.UUID;

public class GameRegistrator {
    private final UUID serverID;
    private final UUID gameId;
    private final String arenaName;
    private final boolean edit;
    private final GameStage gameStage;
    private final List<TeamPlayer> players;
    private final List<TeamPlayer> activePlayers;
    private final ArenaTypes gameType;

    public GameRegistrator(UUID serverID, UUID gameId, String arenaName, boolean edit, GameStage gameStage, List<TeamPlayer> players, List<TeamPlayer> activePlayers, ArenaTypes gameType) {
        this.serverID = serverID;
        this.gameId = gameId;
        this.arenaName = arenaName;
        this.edit = edit;
        this.gameStage = gameStage;
        this.players = players;
        this.activePlayers = activePlayers;
        this.gameType = gameType;
    }


    public UUID getServerID() {
        return serverID;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getArenaName() {
        return arenaName;
    }

    public boolean isEdit() {
        return edit;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public List<TeamPlayer> getActivePlayers() {
        return activePlayers;
    }

    public ArenaTypes getGameType() {
        return gameType;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GameRegistrator fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameRegistrator.class);
    }
}
