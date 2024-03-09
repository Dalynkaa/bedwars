package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.GameStage;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.TeamPlayer;

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

    public GameRegistrator(UUID serverID ,UUID gameId, String arenaName, boolean edit, GameStage gameStage, List<TeamPlayer> players, List<TeamPlayer> activePlayers) {
        this.serverID = serverID;
        this.gameId = gameId;
        this.arenaName = arenaName;
        this.edit = edit;
        this.gameStage = gameStage;
        this.players = players;
        this.activePlayers = activePlayers;
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
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static GameRegistrator fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, GameRegistrator.class);
    }
    public static Integer getServerList(ArenaTypes arenaType){
        int servers = 0;
        for (ServerRegistrator serverRegistrator: SPBedWarsLobby.getInstance().servers.values()){
            if (serverRegistrator.getArenaType().equals(arenaType) && !serverRegistrator.getEdit()){
                servers++;
            }
        }
        return servers;
    }
    public static Integer getServerGamesList(ArenaTypes arenaType){
        int games = 0;
        for (ServerRegistrator serverRegistrator: SPBedWarsLobby.getInstance().servers.values()){
            if (serverRegistrator.getArenaType().equals(arenaType) && !serverRegistrator.getEdit()){
                if (serverRegistrator.getGames() != null){
                    games+= serverRegistrator.getGames().size();
                }else {
                    games+=0;
                }
            }
        }
        return games;
    }
    public static Integer getServerPlayerList(ArenaTypes arenaType){
        int players = 0;
        for (ServerRegistrator serverRegistrator: SPBedWarsLobby.getInstance().servers.values()){
            if (serverRegistrator.getArenaType().equals(arenaType) && !serverRegistrator.getEdit()){
                if (serverRegistrator.getGames() != null) {
                    for (GameRegistrator gameRegistrator : serverRegistrator.getGames()) {
                        if (gameRegistrator.getPlayers() != null) {
                            players += gameRegistrator.getPlayers().size();
                        } else {
                            players += 0;
                        }
                    }
                }else {
                    players+=0;
                }
            }
        }
        return players;
    }
    public static GameRegistrator findSuitableGame(ArenaTypes arenaType) {
        GameRegistrator suitableGame = null;
        for (ServerRegistrator server : SPBedWarsLobby.getInstance().servers.values()) {
            if (server.getArenaType() == arenaType && server.getGames() != null && !server.getEdit()) {
                for (GameRegistrator game : server.getGames()) {
                    if (game.getGameStage() == GameStage.WAITING) {
                        if (!game.getPlayers().isEmpty() && game.getPlayers().size() < arenaType.getPlayers()) {
                            return game;
                        } else if (suitableGame == null ||
                                (suitableGame.getPlayers().size() < arenaType.getPlayers() &&
                                        !game.getPlayers().isEmpty())) {
                            suitableGame = game;
                        }
                    }
                }
            }
        }
        return suitableGame;
    }
}
