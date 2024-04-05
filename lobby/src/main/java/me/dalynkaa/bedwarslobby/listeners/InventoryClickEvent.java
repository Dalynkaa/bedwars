package me.dalynkaa.bedwarslobby.listeners;


import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.utils.usable.IInventoryButton;
import me.dalynkaa.bedwarslobby.utils.usable.InventoryButton;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClickEvent implements Listener {
    SPBedWarsLobby main;

    public InventoryClickEvent(SPBedWarsLobby spBedWarsLobby) {
        spBedWarsLobby.getServer().getPluginManager().registerEvents(this, spBedWarsLobby);
        this.main = spBedWarsLobby;

    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getMaterial().equals(Material.AIR)) {
            return;
        }
        PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
        if (!container.has(NamespacedKey.fromString("item_id"))) {
            return;
        }
        if (!container.has(NamespacedKey.fromString("click"))) {
            return;
        }
        event.setCancelled(true);
        String item_uuid = container.get(NamespacedKey.fromString("item_id"), PersistentDataType.STRING);
        InventoryButton inventoryButton = main.inventoryMap.get(item_uuid);
        IInventoryButton<PlayerInteractEvent> action = inventoryButton.getAction();
        action.execute(event);
    }


}
