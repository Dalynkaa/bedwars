package me.dalynkaa.bedwarslobby.proxyUtils.data.registrators;

import com.google.gson.Gson;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArenaRegistrator {
    private final UUID serverId;
    private final UUID arenaId;
    private final ArenaTypes arenaType;
    private final String arenaName;

    public ArenaRegistrator(UUID serverId, ArenaTypes arenaType, String arenaName, UUID arenaId) {
        this.serverId = serverId;
        this.arenaType = arenaType;
        this.arenaName = arenaName;
        this.arenaId = arenaId;
    }

    public UUID getServerId() {
        return serverId;
    }

    public ArenaTypes getArenaType() {
        return arenaType;
    }

    public String getArenaName() {
        return arenaName;
    }

    public UUID getArenaId() {
        return arenaId;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static ArenaRegistrator fromJson(String json) {
        Gson gson = new Gson();
        ArenaRegistrator serverRegistrator = gson.fromJson(json, ArenaRegistrator.class);
        return serverRegistrator;
    }

    public static ArenaRegistrator getArenaByName(String arenaName) {
        for (ArenaRegistrator arena : SPBedWarsLobby.getInstance().arenas) {
            if (arena.getArenaName().equals(arenaName)) {
                return arena;
            }
        }
        return null;
    }

    public static ArenaRegistrator selectRandomArena() {
        List<ArenaRegistrator> arenas = SPBedWarsLobby.getInstance().arenas;
        if (arenas.isEmpty()) {
            return null;
        }
        return arenas.get((int) (Math.random() * arenas.size()));
    }

    public static List<ArenaRegistrator> getServerArenas(UUID serverId) {
        if (serverId == null) {
            return null;
        }
        List<ArenaRegistrator> arenas = new ArrayList<>();
        for (ArenaRegistrator arena : SPBedWarsLobby.getInstance().arenas) {
            if (arena.getServerId().equals(serverId)) {
                arenas.add(arena);
            }
        }
        return arenas;
    }
}
