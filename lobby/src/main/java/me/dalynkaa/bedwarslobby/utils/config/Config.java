package me.dalynkaa.bedwarslobby.utils.config;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static void setSpawnLocation(Location location) {
        FileConfiguration config = SPBedWarsLobby.getInstance().getConfig();
        config.set("spawnLocation", location);
        SPBedWarsLobby.getInstance().saveConfig();
    }

    public static Location getSpawnLocation() {
        FileConfiguration config = SPBedWarsLobby.getInstance().getConfig();
        return config.getLocation("spawnLocation");
    }
}
