package me.dalynkaa.spbedwars.utils.usableClasses;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

public class BlockHologramDisplay {
    private final Location location;
    private final Material blockType;
    private ItemDisplay hologramEntity;
    private TextDisplay lvlEntity;
    private TextDisplay typeEntity;
    private TextDisplay spawnEntity;
    private Component lvl;
    private Component type;
    private Component spawn;
    private int currentYaw = 0;

    public BlockHologramDisplay(Location location, Material blockType, Component lvl, Component type, Component spawn) {
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

    public ItemDisplay getHologramEntity() {
        return hologramEntity;
    }

    public BlockHologramDisplay setHologramEntity(ItemDisplay hologramEntity) {
        this.hologramEntity = hologramEntity;
        return this;
    }

    public Component getLvl() {
        return lvl;
    }

    public BlockHologramDisplay setLvl(Component lvl) {
        this.lvl = lvl;
        this.lvlEntity.text(lvl);
        return this;
    }

    public Component getType() {
        return type;
    }

    public BlockHologramDisplay setType(Component type) {
        this.type = type;
        this.typeEntity.text(this.type);
        return this;
    }

    public Component getSpawn() {
        return spawn;
    }

    public BlockHologramDisplay setSpawn(Component spawn) {
        this.spawn = spawn;
        this.spawnEntity.text(this.spawn);
        return this;
    }

    public float getCurrentYaw() {
        return currentYaw;
    }

    public BlockHologramDisplay setCurrentYaw(int currentYaw) {
        this.currentYaw = currentYaw;
        return this;
    }

    public void spawn() {
        // Create the hologram entity at the specified location
        this.hologramEntity = (ItemDisplay) location.getWorld().spawnEntity(location.add(0, 1.5, 0), EntityType.ITEM_DISPLAY);
        this.hologramEntity.setItemStack(new ItemStack(blockType));
        this.hologramEntity.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIXED);
        this.hologramEntity.setBillboard(Display.Billboard.FIXED);
        this.hologramEntity.setInterpolationDuration(20);
        this.hologramEntity.setInterpolationDelay(1);
        this.hologramEntity.setBrightness(new Display.Brightness(15, 15));
        Transformation transformation = hologramEntity.getTransformation();
        transformation.getScale().set(1.5, 1.5, 1.5);
        this.hologramEntity.setTransformation(transformation);
        // create time text
        this.spawnEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 0.5, 0), EntityType.TEXT_DISPLAY);
        this.spawnEntity.text(getSpawn());
        this.spawnEntity.setBillboard(Display.Billboard.CENTER);
        this.spawnEntity.setBrightness(new Display.Brightness(15, 15));
        // create type text
        this.typeEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 0.5, 0), EntityType.TEXT_DISPLAY);
        this.typeEntity.text(getType());
        this.typeEntity.setBillboard(Display.Billboard.CENTER);
        this.typeEntity.setBrightness(new Display.Brightness(15, 15));
        // create lvl text
        this.lvlEntity = (TextDisplay) location.getWorld().spawnEntity(location.add(0, 0.5, 0), EntityType.TEXT_DISPLAY);
        this.lvlEntity.text(getLvl());
        this.lvlEntity.setBillboard(Display.Billboard.CENTER);
        this.lvlEntity.setBrightness(new Display.Brightness(15, 15));
    }

    public void despawn() {
        this.hologramEntity.remove();
        this.spawnEntity.remove();
        this.typeEntity.remove();
        this.lvlEntity.remove();

    }

    public void rotate(int angle) {
        if (currentYaw >= 360) {
            currentYaw -= 360;
        }
        hologramEntity.setInterpolationDuration(20 * 2);
        hologramEntity.setInterpolationDelay(1);
        Transformation transformation = hologramEntity.getTransformation();
        transformation.getLeftRotation().rotateY((float) Math.toRadians(angle));
        hologramEntity.setTransformation(transformation);
        currentYaw += angle;

    }
}
