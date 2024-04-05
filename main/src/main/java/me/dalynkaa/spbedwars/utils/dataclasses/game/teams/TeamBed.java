package me.dalynkaa.spbedwars.utils.dataclasses.game.teams;

import me.dalynkaa.spbedwars.utils.PlayerUtils;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameLocation;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("TeamBed")
public class TeamBed implements ConfigurationSerializable {
    private GameLocation bedPos1;
    private BlockFace face;
    private DyeColor color;


    public TeamBed(GameLocation bedPos1, BlockFace face, DyeColor color) {
        this.bedPos1 = bedPos1;
        this.face = face;
        this.color = color;
    }

    public GameLocation getBedPos1() {
        return bedPos1;
    }

    public TeamBed setBedPos1(GameLocation bedPos1) {
        this.bedPos1 = bedPos1;
        return this;
    }

    public BlockFace getFace() {
        return face;
    }

    public TeamBed setFace(BlockFace face) {
        this.face = face;
        return this;
    }

    public DyeColor getColor() {
        return color;
    }

    public TeamBed setColor(DyeColor color) {
        this.color = color;
        return this;
    }

    public void destroy(BWGame game) {
        getBedPos1().getLocation().getBlock().setType(Material.AIR);
        getBedPos1().getLocation().getBlock().getRelative(getFace()).setType(Material.AIR);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("pos1", getBedPos1());
        map.put("face", getFace().name());
        map.put("color", getColor().name());
        return map;
    }

    public static TeamBed deserialize(Map<String, Object> map) {
        GameLocation bedPos1 = (GameLocation) map.get("pos1");
        BlockFace b_face = BlockFace.valueOf((String) map.get("face"));
        DyeColor b_color = DyeColor.valueOf((String) map.get("color"));
        return new TeamBed(bedPos1, b_face, b_color);
    }

    public static boolean isBed(Material material) {
        return material == Material.RED_BED || material == Material.BLUE_BED || material == Material.LIME_BED || material == Material.YELLOW_BED;
    }

    public void setBedBlock(Player player) {
        Block block = getBedPos1().getLocation().getBlock();
        BlockState bedFoot = block.getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(PlayerUtils.getBlockFace(player)).getState();
        BlockData bedHeadData = Bukkit.getServer().createBlockData("minecraft:" + getColor().name().toLowerCase() + "_bed[facing=" + getFace().name().toLowerCase() + ",occupied=false,part=head]");
        BlockData bedFootData = Bukkit.getServer().createBlockData("minecraft:" + getColor().name().toLowerCase() + "_bed[facing=" + getFace().name().toLowerCase() + ",occupied=false,part=foot]");
        bedFoot.setBlockData(bedFootData);
        bedHead.setBlockData(bedHeadData);
        bedFoot.update(true, false);
        bedHead.update(true, true);
    }

    public void setBedBlock() {
        Block block = getBedPos1().getLocation().getBlock();
        BlockState bedFoot = block.getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(getFace()).getState();
        BlockData bedHeadData = Bukkit.getServer().createBlockData("minecraft:" + getColor().name().toLowerCase() + "_bed[facing=" + getFace().name().toLowerCase() + ",occupied=false,part=head]");
        BlockData bedFootData = Bukkit.getServer().createBlockData("minecraft:" + getColor().name().toLowerCase() + "_bed[facing=" + getFace().name().toLowerCase() + ",occupied=false,part=foot]");
        bedFoot.setBlockData(bedFootData);
        bedHead.setBlockData(bedHeadData);
        bedFoot.update(true, false);
        bedHead.update(true, true);
    }

    @Override
    public String toString() {
        return "TeamBed{" +
                "bedPos1=" + bedPos1 +
                ", face=" + face +
                ", color=" + color +
                '}';
    }
}
