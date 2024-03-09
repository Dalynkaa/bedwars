package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerRegistrator {
    private final UUID serverId;
    private final String serverName;
    private final ArenaTypes arenaType;
    private List<GameRegistrator> games;
    private Boolean isEdit;
    public ServerRegistrator(UUID serverId, String serverName, ArenaTypes arenaType, Boolean isEdit) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.arenaType = arenaType;
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

    public void addGame(GameRegistrator gameRegistrator){
        if (games == null){
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

    public void removeGame(UUID gameId){
        for (GameRegistrator game : games){
            if (game.getGameId().equals(gameId)){
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
    public List<GameRegistrator> getGames(){
        return games;
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
