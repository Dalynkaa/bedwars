package me.dalynkaa.bedwarslobby.utils.usable;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class InventoryButton {
    private ItemStack itemStack;
    private Component name;
    private List<Component> lore;
    private IInventoryButton<PlayerInteractEvent> action;

    public InventoryButton(ItemStack item) {
        this.action = null;
        this.name = null;
        this.lore = null;
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public InventoryButton setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public Component getName() {
        return name;
    }

    public InventoryButton setName(Component name) {
        this.name = name;
        return this;
    }

    public List<Component> getLore() {
        return lore;
    }

    public InventoryButton setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public InventoryButton setLore(Component lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public IInventoryButton<PlayerInteractEvent> getAction() {
        return action;
    }

    public static InventoryButton from(ItemStack stack) {
        return new InventoryButton(stack);
    }

    public static InventoryButton from(Material material) {
        ItemStack stack = new ItemStack(material);
        return new InventoryButton(stack);
    }

    public static InventoryButton fromHead(String url) {
        ItemStack stack = HeadUtils.getCustomHead(url);
        return new InventoryButton(stack);
    }

    public ItemStack build(IInventoryButton<PlayerInteractEvent> action, String id) {
        this.action = action;
        String uuid = UUID.randomUUID().toString();
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.displayName(this.name);
        meta.lore(lore);
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("item_id"), PersistentDataType.STRING, uuid);
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("click"), PersistentDataType.STRING, id);
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("gui_item"), PersistentDataType.BOOLEAN, true);
        this.itemStack.setItemMeta(meta);
        SPBedWarsLobby.getInstance().inventoryMap.put(uuid, this);
        return this.itemStack;
    }
}
