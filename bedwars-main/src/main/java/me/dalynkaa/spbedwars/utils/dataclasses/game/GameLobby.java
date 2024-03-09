package me.dalynkaa.spbedwars.utils.dataclasses.game;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("lobby")
public class GameLobby implements ConfigurationSerializable {
    private GameLocation pos1;
    private GameLocation pos2;
    private GameLocation spawn;

    public GameLobby(GameLocation pos1, GameLocation pos2, GameLocation spawn) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.spawn = spawn;
    }

    public GameLocation getPos1() {
        return pos1;
    }

    public GameLobby setPos1(GameLocation pos1) {
        this.pos1 = pos1;
        return this;
    }

    public GameLocation getPos2() {
        return pos2;
    }

    public GameLobby setPos2(GameLocation pos2) {
        this.pos2 = pos2;
        return this;
    }

    public GameLocation getSpawn() {
        return spawn;
    }

    public GameLobby setSpawn(GameLocation spawn) {
        this.spawn = spawn;
        return this;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("pos1", getPos1());
        map.put("pos2", getPos2());
        map.put("spawn", getSpawn());
        return map;
    }
    public static GameLobby deserialize(Map<String, Object> map) {
        GameLocation pos1S = (GameLocation) map.get("pos1");
        GameLocation pos2S = (GameLocation) map.get("pos2");
        GameLocation spawnS = (GameLocation) map.get("spawn");
        return new GameLobby(pos1S,pos2S,spawnS);
    }

    @Override
    public String toString() {
        return "GameLobby{" +
                "pos1=" + pos1 +
                ", pos2=" + pos2 +
                ", spawn=" + spawn +
                '}';
    }
}
