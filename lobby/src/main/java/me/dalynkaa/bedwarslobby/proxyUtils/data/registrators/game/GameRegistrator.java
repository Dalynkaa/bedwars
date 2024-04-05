package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.game;

import com.google.gson.Gson;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.GameStage;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ServerType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.TeamPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;

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

    public static Integer getServerList(ServerType serverType) {
        int servers = 0;
        for (ServerRegistrator serverRegistrator : SPBedWarsLobby.getInstance().servers.values()) {
            if (serverRegistrator.getServerType().equals(serverType) && !serverRegistrator.getEdit()) {
                servers++;
            }
        }
        return servers;
    }

    public static Integer getServerGamesList(ServerType serverType) {
        int games = 0;
        for (ServerRegistrator serverRegistrator : SPBedWarsLobby.getInstance().servers.values()) {
            if (serverRegistrator.getServerType().equals(serverType) && !serverRegistrator.getEdit()) {
                if (serverRegistrator.getGames() != null) {
                    games += serverRegistrator.getGames().size();
                } else {
                    games += 0;
                }
            }
        }
        return games;
    }

    public static Integer getServerPlayerList(ServerType serverType) {
        int players = 0;
        for (ServerRegistrator serverRegistrator : SPBedWarsLobby.getInstance().servers.values()) {
            if (serverRegistrator.getServerType().equals(serverType) && !serverRegistrator.getEdit()) {
                if (serverRegistrator.getGames() != null) {
                    for (GameRegistrator gameRegistrator : serverRegistrator.getGames()) {
                        if (gameRegistrator.getPlayers() != null) {
                            players += gameRegistrator.getPlayers().size();
                        } else {
                            players += 0;
                        }
                    }
                } else {
                    players += 0;
                }
            }
        }
        return players;
    }

    public static GameRegistrator findSuitableGame(ServerType serverType, ArenaTypes arenaType) {
        GameRegistrator suitableGame = null;
        for (ServerRegistrator server : SPBedWarsLobby.getInstance().servers.values()) {
            if (server.getServerType() == serverType && server.getGames() != null && !server.getEdit()) {
                for (GameRegistrator game : server.getGames()) {
                    if (game.getGameType() == arenaType) {
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
        }
        return suitableGame;
    }
}
