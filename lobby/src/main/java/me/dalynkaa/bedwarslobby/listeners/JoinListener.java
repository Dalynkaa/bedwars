package me.dalynkaa.bedwarslobby.listeners;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.ControllItems;
import me.dalynkaa.bedwarslobby.utils.config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    public JoinListener(SPBedWarsLobby spBedWars) {
        spBedWars.getServer().getPluginManager().registerEvents(this, spBedWars);
    }

    @EventHandler
    public void joinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        event.joinMessage(null);
        Location spawn = Config.getSpawnLocation();
        if (spawn != null) {
            event.getPlayer().teleport(spawn);
        }
        new ControllItems().giveItems(bPlayer);
    }
}
