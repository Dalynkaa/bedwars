package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.MessageType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ServerType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.game.GameRegistrator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerRegistrator {
    private final UUID serverId;
    private final String serverName;
    private final ServerType serverType;
    private List<GameRegistrator> games;
    private Boolean isEdit;

    public ServerRegistrator(UUID serverId, String serverName, ServerType serverType, Boolean isEdit) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.serverType = serverType;
        this.isEdit = isEdit;
    }

    public UUID getServerId() {
        return serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void addGame(GameRegistrator gameRegistrator) {
        if (games == null) {
            games = new ArrayList<>();
        }
        games.add(gameRegistrator);
    }

    public Boolean getEdit() {
        return isEdit;
    }

    public void setEdit(Boolean edit) {
        isEdit = edit;
    }

    public void removeGame(UUID gameId) {
        for (GameRegistrator game : games) {
            if (game.getGameId().equals(gameId)) {
                games.remove(game);
                return;
            }
        }
    }

    public void updateGame(GameRegistrator gameRegistrator) {
        if (games == null) {
            games = new ArrayList<>();
            games.add(gameRegistrator);
        } else {
            int indexToRemove = -1;
            for (int i = 0; i < games.size(); i++) {
                GameRegistrator registrator = games.get(i);
                if (registrator.getGameId().equals(gameRegistrator.getGameId())) {
                    indexToRemove = i;
                    break;
                }
            }
            if (indexToRemove != -1) {
                games.remove(indexToRemove);
                games.add(gameRegistrator);
            }
        }
    }

    public void createGame(ArenaRegistrator arenaRegistrator, BPlayer bPlayer, ArenaTypes arenaTypes) {
        if (SPBedWarsLobby.getInstance().serverIsCreateGame(serverId)) {
            bPlayer.sendMessage("Игра уже создается", MessageType.ERROR);
            return;
        }
        SPBedWarsLobby.getInstance().addGameCreationServer(serverId);
        SPBedWarsLobby.getInstance().getProxyUtils().requestGameCreation(arenaRegistrator.getArenaId(), serverId, bPlayer, arenaTypes);
    }

    public List<GameRegistrator> getGames() {
        return games;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static ServerRegistrator getServerByName(String serverName) {
        for (ServerRegistrator server : SPBedWarsLobby.getInstance().servers.values()) {
            if (server.getServerName().equals(serverName)) {
                return server;
            }
        }
        return null;
    }

    public static ServerRegistrator getFirstServerByType(ServerType serverType) {
        for (ServerRegistrator server : SPBedWarsLobby.getInstance().servers.values()) {
            if (server.getServerType().equals(serverType)) {
                return server;
            }
        }
        return null;
    }

    public static ServerRegistrator fromJson(String json) {
        Gson gson = new Gson();
        ServerRegistrator serverRegistrator = gson.fromJson(json, ServerRegistrator.class);
        return serverRegistrator;
    }
}
