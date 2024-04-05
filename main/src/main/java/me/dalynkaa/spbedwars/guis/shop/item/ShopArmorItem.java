package me.dalynkaa.spbedwars.guis.shop.item;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.TeamUpgrades;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ShopArmorItem {
    private ItemStack itemStack;
    private Material cost;
    private ArmorType armorType;
    private Integer costCount;
    private Component name;
    private List<Component> lore;
    public ShopArmorItem(ItemStack item){
        this.name = null;
        this.lore = null;
        this.cost = null;
        this.armorType = null;
        this.costCount = null;
        this.itemStack = item;
    }
    public ShopArmorItem(){
        this.name = null;
        this.lore = null;
        this.cost = null;
        this.armorType = null;
        this.costCount = null;
        this.itemStack = null;
    }


    public Material getCost() {
        return cost;
    }

    public ShopArmorItem setCost(Material cost, Integer costCount) {
        this.costCount = costCount;
        this.cost = cost;
        return this;
    }

    public Integer getCostCount() {
        return costCount;
    }

    public ArmorType getArmorType() {
        return this.armorType;
    }
    public ShopArmorItem setArmorType(ArmorType armorType) {
        this.armorType = armorType;
        return this;
    }
    public Material getMaterial() {
        switch (armorType){
            case STONE:
                return Material.CHAINMAIL_BOOTS;
            case IRON:
                return Material.IRON_BOOTS;
            case DIAMOND:
                return Material.DIAMOND_BOOTS;
        }
        return null;
    }


    public ItemStack getItemStack() {
        return itemStack;
    }

    public ShopArmorItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public Component getName() {
        return name;
    }

    public ShopArmorItem setName(Component name) {
        this.name = name;
        return this;
    }

    public List<Component> getLore() {
        return lore;
    }

    public ShopArmorItem setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }
    public ShopArmorItem setLore(Component lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }


    public static ShopArmorItem builder(){
        return new ShopArmorItem();
    }
    public GuiItem build(){
        ItemBuilder builder = ItemBuilder.from(getMaterial());
        builder.name(getName());

        List<Component> loreTemp = getLore();
//        loreTemp.add(Component.text("Стоимость - ")
//                .append(Component.text(costCount))
//                .append(Component.text(" "+getTranslatedMaterial(getMaterial(), getCount()))));
        builder.lore(Arrays.asList(Component.text("Цена: ", TextColor.fromCSSHexString("#6c5ce7")).append(getTranslatedMaterial(getCost(), getCostCount()))));
        GuiItem guiItem = builder.asGuiItem(this::action);
        return guiItem;
    }

    public void action(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        TeamPlayer teamPlayer = BPlayer.getByUUID(player.getUniqueId()).getTeamPlayer();
        if (!hasItemCount(player, getCost(), getCostCount())){
            return;
        }
        removeItems(player, getCost(), getCostCount());
        ItemStack leggings;
        ItemStack boots;
        switch (armorType){
            case STONE:
                leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                boots = new ItemStack(Material.CHAINMAIL_BOOTS);
                break;
            case IRON:
                leggings = new ItemStack(Material.IRON_LEGGINGS);
                boots = new ItemStack(Material.IRON_BOOTS);
                break;
            case DIAMOND:
                leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                boots = new ItemStack(Material.DIAMOND_BOOTS);
                break;
            default:
                leggings = null;
                boots = null;
        }
        ItemMeta leggingsMeta = leggings.getItemMeta();
        ItemMeta bootsMeta = boots.getItemMeta();

        leggingsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        leggingsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, TeamPlayer.ItemType.ARMOR.name());
        bootsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        bootsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, TeamPlayer.ItemType.ARMOR.name());
        if (teamPlayer.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR1)){
            leggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            bootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        }
        if (teamPlayer.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR2)) {
            leggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
            bootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        }
        if (teamPlayer.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR3)) {
            leggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
            bootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        }
        if (teamPlayer.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR4)) {
            leggingsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
            bootsMeta.addEnchant(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        }
        leggings.setItemMeta(leggingsMeta);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

    }

    public boolean hasItemCount(Player player, Material material, int count) {
        int itemCount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                itemCount += item.getAmount();
                if (itemCount >= count) {
                    return true;
                }
            }
        }
        return itemCount >= count;
    }

    public boolean removeItems(Player player, Material material, int count) {
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length && count > 0; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() == material) {
                int amount = item.getAmount();
                if (amount <= count) {
                    contents[i] = null;
                    count -= amount;
                } else {
                    item.setAmount(amount - count);
                    count = 0;
                }
            }
        }
        player.getInventory().setContents(contents);
        return count == 0;
    }
    private Component getTranslatedMaterial(Material material, Integer count){
        switch (material){
            case IRON_INGOT -> {
                return Component.text(count, TextColor.fromCSSHexString("#dfe6e9")).append(Component.text(" железа", TextColor.fromCSSHexString("#b2bec3")));
            }
            case GOLD_INGOT -> {
                return Component.text(count, TextColor.fromCSSHexString("#ffeaa7")).append(Component.text(" золота", TextColor.fromCSSHexString("#fdcb6e")));
            }
            case DIAMOND -> {
                return Component.text(count, TextColor.fromCSSHexString("#74b9ff")).append(Component.text(" алмаза", TextColor.fromCSSHexString("#0984e3")));
            }
            case EMERALD -> {
                return Component.text(count, TextColor.fromCSSHexString("#55efc4")).append(Component.text(" изумруда", TextColor.fromCSSHexString("#00b894")));
            }
            default -> {
                return Component.text("Неизвестный материал");
            }
        }
    }
    public enum ArmorType {
        STONE, IRON, DIAMOND;
    }
}
