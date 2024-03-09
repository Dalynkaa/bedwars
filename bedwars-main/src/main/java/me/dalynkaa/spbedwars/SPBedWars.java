package me.dalynkaa.spbedwars;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import io.lumine.mythic.bukkit.utils.adventure.platform.bukkit.BukkitAudiences;
import me.dalynkaa.spbedwars.commands.arenacommand.ArenaManager;
import me.dalynkaa.spbedwars.commands.bedwars.BedWarsManager;
import me.dalynkaa.spbedwars.commands.gameCommand.GameManager;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import me.dalynkaa.spbedwars.huds.MainHudRenderer;
import me.dalynkaa.spbedwars.mainListener.*;
import me.dalynkaa.spbedwars.proxyUtils.BungeeMessanging;
import me.dalynkaa.spbedwars.proxyUtils.ProxyUtils;
import me.dalynkaa.spbedwars.proxyUtils.data.GameJoinRegistrator;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.PlaceholderApis;
import me.dalynkaa.spbedwars.utils.database.DatabaseController;
import me.dalynkaa.spbedwars.utils.dataclasses.another.ArenaCreation;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CuboidHighlighter;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CustomSkin;
import me.dalynkaa.spbedwars.utils.dataclasses.game.*;
import me.dalynkaa.spbedwars.utils.dataclasses.game.teams.TeamBed;
import me.dalynkaa.spbedwars.utils.usableClasses.InventoryButton;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SPBedWars extends JavaPlugin {
    public Component PREFIX = Component.text("[", TextColor.fromCSSHexString("#ff7675"))
            .append(Component.text("BedWars", TextColor.fromCSSHexString("#d63031")))
            .append(Component.text("]", TextColor.fromCSSHexString("#ff7675")));
    private DatabaseController databaseController;
    private static SPBedWars inctance;
    public HashMap<UUID, ShopGui> shopGuiMap = new HashMap<>();
    public HashMap<String, InventoryButton> inventoryMap = new HashMap<>();
    public HashMap<UUID, GameJoinRegistrator> gameJoinTemp = new HashMap<UUID, GameJoinRegistrator>();
    public List<CuboidHighlighter> particles = new ArrayList<>();
    public HashMap<UUID, BWGame> activeGames = new HashMap<>();
    public HashMap<UUID, BWGame> editsGames = new HashMap<>();
    public ArenaCreation currentCreation;
    public ProxyUtils proxyUtils;
    public HashMap<UUID, Integer> timerMap = new HashMap<>();
    public String lobbyServerName = "lobby";
    private File hudConfigFile;
    private FileConfiguration hudConfig;
    public boolean debug = true;
    public boolean gameRegistered = false;
    private ProtocolManager manager;

    @Override
    public void onLoad() {
        manager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        //init plugin
        setInctance(this);
        saveDefaultConfig();
        //config
        initConfig();
        //init proxy
        new BungeeMessanging(this);
        this.proxyUtils = new ProxyUtils(this);
        // connect to database
        try {
            databaseController = new DatabaseController(this);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //
        //protocol lib
        Logger.debug(manager.getMinecraftVersion().getVersion() + " Minecraft version");
        // listeners
        new PreJoinListener(this);
        new InventoryItemInteract(this);
        new AllTickListeners(this);
        new JoinListener(this);
        new BlockListener(this);
        new PlayerDeathListener(this);
        new TntListener(this);
        new NpcClickListener(this);

        //commands
        getCommand("arena").setExecutor(new ArenaManager());
        getCommand("game").setExecutor(new GameManager());
        getCommand("bedwars").setExecutor(new BedWarsManager());

        //Placeholder addon
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderApis(this).register();
        }
        BWGame.loadGames();

        //hud
        MainHudRenderer.startRenderer();
    }

    public static SPBedWars getInstance() {
        return inctance;
    }

    private void setInctance(SPBedWars inct) {
        inctance = inct;
    }

    public DatabaseController getDatabase() {
        return this.databaseController;
    }

    private void createHudConfig() {
        hudConfigFile = new File(getDataFolder(), "hudconfig.yml");
        if (!hudConfigFile.exists()) {
            hudConfigFile.getParentFile().mkdirs();
            saveResource("hudconfig.yml", false);
        }

        hudConfig = new YamlConfiguration();
        try {
            hudConfig.load(hudConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void initConfig() {
        createHudConfig();
        ConfigurationSerialization.registerClass(GameLocation.class, "GameLocation");
        ConfigurationSerialization.registerClass(GameArena.class, "GameArena");
        ConfigurationSerialization.registerClass(GameTeam.class, "GameTeam");
        ConfigurationSerialization.registerClass(GameLobby.class, "GameLobby");
        ConfigurationSerialization.registerClass(GameShop.class, "GameShop");
        ConfigurationSerialization.registerClass(GameSpawner.class, "GameSpawner");
        ConfigurationSerialization.registerClass(TeamBed.class, "TeamBed");
        ConfigurationSerialization.registerClass(CustomSkin.class, "CustomSkin");
    }

    @Override
    public void onDisable() {
        this.proxyUtils.unregisterServer();
        Bukkit.getBossBars().forEachRemaining(keyedBossBar -> {
            Bukkit.removeBossBar(keyedBossBar.getKey());
        });
    }
}
