package me.dalynkaa.spbedwars.utils.config;

import me.dalynkaa.spbedwars.SPBedWars;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SkinConfig {
    private File customConfigFile;
    private FileConfiguration customConfig;
    private final SPBedWars main;
    public SkinConfig(SPBedWars main, String arena){
        this.main = main;
        try {
            createSkinConfig(arena);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
    public void saveCustomConfig() throws IOException {
        this.customConfig.save(this.customConfigFile);
    }
    public void reloadCustomConfig(){
        try {
            this.customConfig.load(this.customConfigFile);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    private void createSkinConfig(String skin) throws IOException {
        this.customConfigFile = new File(new File(this.main.getDataFolder().getPath(), "skins"), skin+".yml");
        if (!this.customConfigFile.exists()) {
            this.customConfigFile.getParentFile().mkdirs();
            this.customConfigFile.createNewFile();
        }
        this.customConfig = new YamlConfiguration();
        try {
            this.customConfig.load(this.customConfigFile);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getAllSkinsConfig(){
        File dir = new File(SPBedWars.getInstance().getDataFolder().getPath(), "skins");
        return Arrays.stream(dir.list()).toList();
    }
    public static Boolean hasSkinConfig(String skin){
        List<String> skins = getAllSkinsConfig();
        return skins.contains(skin+".yml");
    }
    public static SkinConfig getSkinConfig(String skin){
        return new SkinConfig(SPBedWars.getInstance(),skin);
    }
}
