package me.dalynkaa.spbedwars.utils.dataclasses.game;

import com.google.gson.Gson;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SerializableAs("GameLocation")
public class GameLocation implements ConfigurationSerializable {
    private Integer x;
    private Integer y;
    private Integer z;
    private String world;

    public GameLocation(Integer x, Integer y, Integer z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public GameLocation setX(Integer x) {
        this.x = x;
        return this;
    }

    public GameLocation addX(Integer x1) {
        this.x = this.x + x1;
        return this;
    }

    public GameLocation addY(Integer y1) {
        this.y = this.y + y1;
        return this;
    }

    public GameLocation addZ(Integer z1) {
        this.z = this.z + z1;
        return this;
    }

    public GameLocation setY(Integer y) {
        this.y = y;
        return this;
    }

    public GameLocation setZ(Integer z) {
        this.z = z;
        return this;
    }

    public GameLocation setWorld(String world) {
        this.world = world;
        return this;
    }

    public World getWorld() {
        return org.bukkit.Bukkit.getWorld(world);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getZ() {
        return z;
    }

    public String getWorldName() {
        return world;
    }


    public Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }

    public static GameLocation fromLocation(Location location) {
        return new GameLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
    }

    public BlockVector3 getBlockVector3() {
        return BlockVector3.at(getX(), getY(), getZ());
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

    public static GameLocation fromJson(String data) {
        Gson gson = new Gson();
        GameLocation json = gson.fromJson(data, GameLocation.class);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameLocation that = (GameLocation) o;
        return Objects.equals(getX(), that.getX()) && Objects.equals(getY(), that.getY()) && Objects.equals(getZ(), that.getZ());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        return "PlotLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", getX());
        map.put("y", getY());
        map.put("z", getZ());
        map.put("world", getWorldName());
        return map;
    }

    public static GameLocation deserialize(Map<String, Object> map) {
        int XPos = (int) map.get("x");
        int YPos = (int) map.get("y");
        int ZPos = (int) map.get("z");
        String worldName = (String) map.get("world");
        return new GameLocation(XPos, YPos, ZPos, worldName);
    }

}
