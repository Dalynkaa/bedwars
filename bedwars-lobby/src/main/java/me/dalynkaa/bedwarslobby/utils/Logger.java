package me.dalynkaa.bedwarslobby.utils;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import org.bukkit.Bukkit;

public class Logger {
    public Logger() {

    }
    public static void info(String message) {
        Bukkit.getLogger().info(message);
    }
    public void warn(String message) {
        Bukkit.getLogger().info(message);
    }
    public void error(String message) {
        Bukkit.getLogger().info(message);
    }
    public static void debug(String message) {
        if (SPBedWarsLobby.getInstance().debug) {
            Bukkit.getLogger().info("[DEBUG] " + message);
        }
    }
}
