package me.dalynkaa.bedwarslobby;

import me.dalynkaa.bedwarslobby.commands.gameCommand.GameManager;
import me.dalynkaa.bedwarslobby.commands.menuCommand.MenuCommand;
import me.dalynkaa.bedwarslobby.commands.serverCommand.ServerManager;
import me.dalynkaa.bedwarslobby.commands.settingsCommand.SettingsManager;
import me.dalynkaa.bedwarslobby.listeners.*;
import me.dalynkaa.bedwarslobby.proxyUtils.ProxyUtils;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ArenaRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import me.dalynkaa.bedwarslobby.utils.database.DatabaseController;
import me.dalynkaa.bedwarslobby.utils.usable.InventoryButton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public final class SPBedWarsLobby extends JavaPlugin {
    public Component PREFIX = Component.text("[", TextColor.fromCSSHexString("#ff7675"))
            .append(Component.text("BedWars", TextColor.fromCSSHexString("#d63031")))
            .append(Component.text("]", TextColor.fromCSSHexString("#ff7675")));
    private ProxyUtils proxyUtils;
    private DatabaseController databaseController;
    public HashMap<String, InventoryButton> inventoryMap = new HashMap<>();
    private static SPBedWarsLobby spBedWarsLobby;
    public Map<UUID, ServerRegistrator> servers;
    public List<UUID> gameCreationServersHashes;
    public List<ArenaRegistrator> arenas = new ArrayList<>();
    public boolean debug = true;

    //loading plasmo voice
    // Addon class annotated with @Addon.


    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "bedwars:move");
        saveDefaultConfig();
        setInctance(this);
        servers = new HashMap<>();
        gameCreationServersHashes = new ArrayList<>();
        proxyUtils = new ProxyUtils(this);
        // Plugin startup logic
        getCommand("gameServers").setExecutor(new ServerManager());
        getCommand("game").setExecutor(new GameManager());
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("settings").setExecutor(new SettingsManager());
        // connect to database
        try {
            databaseController = new DatabaseController(this);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //events
        new JoinListener(this);
        new MarkerEnterListener(this);
        new DamageCanseller(this);
        new GuiItemsInteract(this);
        new InventoryClickEvent(this);
        new JumpPadListener(this);
        new RestrictedActionsListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SPBedWarsLobby getInstance() {
        return spBedWarsLobby;
    }

    private void setInctance(SPBedWarsLobby inct) {
        spBedWarsLobby = inct;
    }

    public ProxyUtils getProxyUtils() {
        return proxyUtils;
    }

    public DatabaseController getDatabase() {
        return this.databaseController;
    }

    public boolean serverIsCreateGame(UUID serverId) {
        return gameCreationServersHashes.contains(serverId);
    }

    public void addGameCreationServer(UUID serverId) {
        gameCreationServersHashes.add(serverId);
    }

    public void removeGameCreationServer(UUID serverId) {
        gameCreationServersHashes.remove(serverId);
    }
}
