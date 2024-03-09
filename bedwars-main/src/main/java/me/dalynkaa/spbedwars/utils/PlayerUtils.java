package me.dalynkaa.spbedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static BlockFace getBlockFace(Player p) {
        float yaw = p.getLocation().getYaw();
        if (yaw > 135 || yaw < -135) {
            return BlockFace.NORTH;
        } else if (yaw < -45) {
            return BlockFace.EAST;
        } else if (yaw > 45) {
            return BlockFace.WEST;
        } else {
            return BlockFace.SOUTH;
        }
    }
}
