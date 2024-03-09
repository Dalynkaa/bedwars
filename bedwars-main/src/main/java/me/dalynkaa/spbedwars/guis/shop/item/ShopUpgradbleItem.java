package me.dalynkaa.spbedwars.guis.shop.item;

import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class ShopUpgradbleItem {

    private Map<Integer, ShopItem> upgrades;
    private Integer maxLvlItem;
    private TeamPlayer.ItemType type;

    public ShopUpgradbleItem() {
        this.upgrades = new HashMap<>();
        this.maxLvlItem = 0;
        this.type = null;
    }

    private ShopUpgradbleItem(Integer maxLvlItem, TeamPlayer.ItemType type) {
        upgrades = new HashMap<>();
        this.maxLvlItem = maxLvlItem;
        this.type = type;

    }

    public Map<Integer, ShopItem> getUpgrades() {
        return upgrades;
    }

    public Integer getMaxLvlItem() {
        return maxLvlItem;
    }

    public TeamPlayer.ItemType getType() {
        return type;
    }

    public static ShopUpgradbleItem builder(Integer maxLvlItem, TeamPlayer.ItemType type) {
        return new ShopUpgradbleItem(maxLvlItem, type);
    }

    public ShopUpgradbleItem setType(TeamPlayer.ItemType type) {
        this.type = type;
        return this;
    }

    public ShopUpgradbleItem setLvlItem(Integer lvl, ShopItem shopItem) {
        if (lvl > maxLvlItem) {
            return this;
        }
        ShopItem shopItem1 = shopItem.addPercistData("upgrade", true).addPercistData("lvl", lvl).addPercistData("type", getType().name());
        upgrades.put(lvl, shopItem1);
        return this;
    }

    public void getLvlItem(Integer lvl) {
        upgrades.get(lvl);
    }

    public void removeLvlItem(Integer lvl) {
        upgrades.remove(lvl);
    }

    public void clear() {
        upgrades.clear();
    }

    public ShopItem getPlayerShopItem(Integer lvl) {
        return upgrades.get(lvl);
    }

    public static Integer getPlayerItemLvl(Player player, TeamPlayer.ItemType type) {
        Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                continue;
            }
            if (item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("upgrade"), PersistentDataType.BOOLEAN)) {
                if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(type.name())) {
                    return item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("lvl"), PersistentDataType.INTEGER);
                }
            }
        }
        return 0;
    }

}
