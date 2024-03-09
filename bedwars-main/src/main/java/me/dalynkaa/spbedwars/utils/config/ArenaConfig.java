package me.dalynkaa.spbedwars.utils.config;

import me.dalynkaa.spbedwars.SPBedWars;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaConfig {
    private File customConfigFile;
    private FileConfiguration customConfig;
    SPBedWars main;

    public ArenaConfig(SPBedWars main, String arena) {
        this.main = main;
        try {
            createArenaConfig(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void saveCustomConfig() throws IOException {
        this.customConfig.save(this.customConfigFile);
    }

    public void reloadCustomConfig() {
        try {
            this.customConfig.load(this.customConfigFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createArenaConfig(String arena) throws IOException {
        File dir = new File(this.main.getDataFolder().getPath(), "arenas");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File arenaFolder = new File(dir, arena);
        this.customConfigFile = new File(arenaFolder, "arena.yml");
        if (!this.customConfigFile.exists()) {
            this.customConfigFile.getParentFile().mkdirs();
            this.customConfigFile.createNewFile();
            //this.main.saveResource(arena+".yml", false);
        }
        this.customConfig = new YamlConfiguration();
        try {
            this.customConfig.load(this.customConfigFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getAllArenasConfig() {
        File dir = new File(SPBedWars.getInstance().getDataFolder().getPath(), "arenas");
        ArrayList<String> arenas = new ArrayList<>();
        for (String arena : dir.list()) {
            File arenaConfig = new File(dir, arena);
            if (arenaConfig.exists()) {
                arenas.add(arena);
            }
        }
        Bukkit.getLogger().info(arenas.toString() + " arenas");
        return arenas;
    }

    public static Boolean hasArenaConfig(String arena) {
        List<String> arenas = getAllArenasConfig();
        return arenas.contains(arena);
    }

    public static ArenaConfig getArenaConfig(String arena) {
        ArenaConfig arenaConfig = new ArenaConfig(SPBedWars.getInstance(), arena);
        return arenaConfig;
    }


}
