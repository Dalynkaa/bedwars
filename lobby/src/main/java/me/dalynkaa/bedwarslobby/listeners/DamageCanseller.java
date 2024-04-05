package me.dalynkaa.bedwarslobby.listeners;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageCanseller implements Listener {

    public DamageCanseller(SPBedWarsLobby spBedWarsLobby) {
        spBedWarsLobby.getServer().getPluginManager().registerEvents(this, spBedWarsLobby);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}
