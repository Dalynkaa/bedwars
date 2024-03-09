package me.dalynkaa.spbedwars.utils;

import me.dalynkaa.spbedwars.SPBedWars;
import org.bukkit.Bukkit;

public class Logger {
    public Logger() {

    }
    public static void info(String message) {
        Bukkit.getLogger().info("\u001B[0m" + message);
    }

    public static void warn(String message) {
        Bukkit.getLogger().warning("\u001B[33m" + message);
    }

    public static void error(String message) {
        Bukkit.getLogger().info("\u001B[31m" + "[ERROR] " + message);
    }

    public static void debug(String message) {
        if (SPBedWars.getInstance().debug) {
            Bukkit.getLogger().info("\u001B[34m" + "[DEBUG] " + message);
        }
    }
}
