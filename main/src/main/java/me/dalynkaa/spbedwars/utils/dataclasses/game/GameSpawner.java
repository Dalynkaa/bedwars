package me.dalynkaa.spbedwars.utils.dataclasses.game;

import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ItemSpawner;
import me.dalynkaa.spbedwars.utils.usableClasses.BlockHologramDisplay;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("spawner")
public class GameSpawner implements ConfigurationSerializable {
    private GameLocation location;
    private ItemSpawner type;
    private boolean hologram;
    private BlockHologramDisplay blockHologram;
    private Integer lvl;
    private Long lastSpawn;

    public GameSpawner(GameLocation location, ItemSpawner type, boolean hologram) {
        this.location = location;
        this.type = type;
        this.hologram = hologram;
        this.lastSpawn = 0L;
        this.lvl = 1;
    }

    public GameLocation getLocation() {
        return location;
    }

    public GameSpawner setLocation(GameLocation location) {
        this.location = location;
        return this;
    }

    public ItemSpawner getType() {
        return type;
    }

    public GameSpawner setType(ItemSpawner type) {
        this.type = type;
        return this;
    }

    public boolean isHologram() {
        return hologram;
    }

    public GameSpawner setHologram(boolean hologram) {
        this.hologram = hologram;
        return this;
    }

    public Integer getLvl() {
        return lvl;
    }

    public GameSpawner setLvl(Integer lvl) {
        this.lvl = lvl;
        return this;
    }

    public BlockHologramDisplay getBlockHologram() {
        return blockHologram;
    }

    public GameSpawner setBlockHologram(BlockHologramDisplay blockHologram) {
        this.blockHologram = blockHologram;
        return this;
    }

    public Long getLastSpawn() {
        return lastSpawn;
    }

    public GameSpawner setLastSpawn(Long lastSpawn) {
        this.lastSpawn = lastSpawn;
        return this;
    }

    public void create() {
        if (getType().equals(ItemSpawner.DIAMOND) || getType().equals(ItemSpawner.EMERALD)) {
            BlockHologramDisplay hologram1 = new BlockHologramDisplay(getLocation().getLocation().add(0.5, 1.5, 0.5),
                    getType().getHologram(),
                    Component.text("Уровень ", getType().getMainCollor()).append(Component.text(1, getType().getSecondCollor())),
                    Component.text(getType().getName(), getType().getMainCollor()),
                    Component.text("До спавна ", getType().getMainCollor()).append(Component.text(0, getType().getSecondCollor())));
            hologram1.spawn();
            Logger.debug("Hologram created " + hologram1.getHologramEntity().getName());
            setBlockHologram(hologram1);
        }
    }

    public void despawn() {
        if (getBlockHologram() != null) {
            getBlockHologram().despawn();
        }
    }

    public void itemTick() {
        long currentTime = System.currentTimeMillis();
        if (getType().equals(ItemSpawner.DIAMOND) || getType().equals(ItemSpawner.EMERALD)) {
            getBlockHologram().setSpawn(Component.text("До спавна ", getType().getMainCollor()).append(Component.text((((getLastSpawn() + getType().getSpawnCoolDown(getLvl()) * 1000L) - currentTime) / 1000) % 60, getType().getSecondCollor())));
        }
        if (currentTime < getLastSpawn() + getType().getSpawnCoolDown(getLvl()) * 1000L) {
            return;
        }
        setLastSpawn(currentTime);
        getLocation().getWorld().dropItem(getLocation().getLocation().add(0.5, 0, 0.5), new ItemStack(getType().getItem()));
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("location", getLocation());
        map.put("type", getType().name());
        map.put("hologram", isHologram());
        return map;
    }

    public static GameSpawner deserialize(Map<String, Object> map) {
        GameLocation gameLocation = (GameLocation) map.get("location");
        String type1 = (String) map.get("type");
        Boolean ishologram = (Boolean) map.get("hologram");
        return new GameSpawner(gameLocation, ItemSpawner.valueOf(type1), ishologram);
    }

    @Override
    public String toString() {
        return "GameSpawner{" +
                "location=" + location +
                ", type=" + type +
                ", hologram=" + hologram +
                ", blockHologram=" + blockHologram +
                ", lastSpawn=" + lastSpawn +
                '}';
    }
}
