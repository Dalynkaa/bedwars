package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.usableClasses.IInventoryButton;
import me.dalynkaa.spbedwars.utils.usableClasses.InventoryButton;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryItemInteract implements Listener {
    SPBedWars main;
    public InventoryItemInteract(SPBedWars spBuildRevrited){
        spBuildRevrited.getServer().getPluginManager().registerEvents(this,spBuildRevrited);
        this.main = spBuildRevrited;

    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event){
        if (event.getMaterial().equals(Material.AIR)){
            return;
        }
        PersistentDataContainer container = event.getItem().getItemMeta().getPersistentDataContainer();
        if (!container.has(NamespacedKey.fromString("item_id"))){
            return;
        }
        String item_uuid = container.get(NamespacedKey.fromString("item_id"), PersistentDataType.STRING);
        InventoryButton inventoryButton = main.inventoryMap.get(item_uuid);
        IInventoryButton<PlayerInteractEvent> action = inventoryButton.getAction();
        action.execute(event);
        event.setCancelled(true);
    }
    @EventHandler
    public void itemDropEvent(PlayerDropItemEvent event){
        ItemMeta meta = event.getItemDrop().getItemStack().getItemMeta();
        if (meta == null){
            return;
        }
        if (meta.getPersistentDataContainer().has(NamespacedKey.fromString("gui_item"))){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void itemMoveEvent(InventoryMoveItemEvent event){
        ItemMeta meta = event.getItem().getItemMeta();
        if (meta == null){
            return;
        }
        if (meta.getPersistentDataContainer().has(NamespacedKey.fromString("gui_item"))){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void itemInteractEvent(PlayerInteractEvent event){
        if (event.getItem() == null){
            return;
        }
        ItemMeta meta = event.getItem().getItemMeta();
        if (meta.getPersistentDataContainer().has(NamespacedKey.fromString("gui_item"))){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void itemClickEvent(InventoryClickEvent event){
        if (event.getCurrentItem() == null){
            return;
        }
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (meta == null){
            return;
        }
        if (meta.getPersistentDataContainer().has(NamespacedKey.fromString("gui_item"))){
            event.setCancelled(true);
        }
    }
}
