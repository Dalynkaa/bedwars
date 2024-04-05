package me.dalynkaa.spbedwars.utils.usableClasses;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;

public class BlockHologram {
    private final Location location;
    private final Material blockType;
    private ArmorStand hologramEntity;
    private TextDisplay lvlEntity;
    private TextDisplay typeEntity;
    private TextDisplay spawnEntity;
    private Component lvl;
    private Component type;
    private Component spawn;
    private float currentYaw = 0.0f;

    public BlockHologram(Location location, Material blockType, Component lvl, Component type, Component spawn) {
        this.location = location;
        this.blockType = blockType;
        this.lvl = lvl;
        this.type = type;
        this.spawn = spawn;
    }

    public Location getLocation() {
        return location;
    }

    public Material getBlockType() {
        return blockType;
    }

    public ArmorStand getHologramEntity() {
        return hologramEntity;
    }

    public BlockHologram setHologramEntity(ArmorStand hologramEntity) {
        this.hologramEntity = hologramEntity;
        return this;
    }

    public Component getLvl() {
        return lvl;
    }

    public BlockHologram setLvl(Component lvl) {
        this.lvl = lvl;
        this.lvlEntity.text(lvl);
        return this;
    }

    public Component getType() {
        return type;
    }

    public BlockHologram setType(Component type) {
        this.type = type;
        this.typeEntity.text(this.type);
        return this;
    }

    public Component getSpawn() {
        return spawn;
    }

    public BlockHologram setSpawn(Component spawn) {
        this.spawn = spawn;
        this.spawnEntity.text(this.spawn);
        return this;
    }

    public float getCurrentYaw() {
        return currentYaw;
    }

    public BlockHologram setCurrentYaw(float currentYaw) {
        this.currentYaw = currentYaw;
        return this;
    }

    public void spawn() {
        // Create the hologram entity at the specified location
        hologramEntity = location.getWorld().spawn(location, ArmorStand.class);
        hologramEntity.setGravity(false);
        hologramEntity.setArms(false);
        hologramEntity.setBasePlate(false);
        hologramEntity.setVisible(false);
        hologramEntity.setSmall(true);
        hologramEntity.setHelmet(new ItemStack(blockType));
        // create time text
        this.spawnEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 1.5, 0), EntityType.TEXT_DISPLAY);
        this.spawnEntity.text(getSpawn());
        this.spawnEntity.setBillboard(Display.Billboard.CENTER);
        // create type text
        this.typeEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 0.5, 0), EntityType.TEXT_DISPLAY);
        this.typeEntity.text(getType());
        this.typeEntity.setBillboard(Display.Billboard.CENTER);
        // create lvl text
        this.lvlEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 0.5, 0), EntityType.TEXT_DISPLAY);
        this.lvlEntity.text(getLvl());
        this.lvlEntity.setBillboard(Display.Billboard.CENTER);
    }
    public void despawn(){
        this.hologramEntity.remove();
        this.spawnEntity.remove();
        this.typeEntity.remove();
        this.lvlEntity.remove();

    }

    public void rotate(float angle) {
        currentYaw += angle;
        if (currentYaw >= 360.0f) {
            currentYaw -= 360.0f;
        }
        Location loc = hologramEntity.getLocation();
        loc.setYaw(currentYaw);
        hologramEntity.teleport(loc);
    }
}
