package me.dalynkaa.spbedwars.guis.shop.item;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import me.dalynkaa.spbedwars.guis.shop.shopactions.IActionButton;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.TeamUpgrades;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ShopItem {
    private ItemStack itemStack;
    private Material material;
    private Material cost;
    private Integer costCount;
    private Integer count;
    private Component name;
    private List<Component> lore;
    private Map<Enchantment, Integer> enchantments;
    private Map<String, Object> persistentDataContainer;
    private IActionButton<ShopItem, Player> customAction;
    private TeamPlayer.ItemType itemType;
    private ShopGui gui;
    private boolean replace;

    public ShopItem(ItemStack item){
        this.name = null;
        this.lore = null;
        this.material = null;
        this.cost = null;
        this.costCount = null;
        this.count = 1;
        this.enchantments = null;
        this.persistentDataContainer = null;
        this.itemStack = item;
        this.customAction = null;
        this.gui = null;
        this.replace = false;
        this.itemType = null;
    }
    public ShopItem(){
        this.name = null;
        this.lore = null;
        this.material = null;
        this.cost = null;
        this.costCount = null;
        this.count = 1;
        this.enchantments = null;
        this.persistentDataContainer = new HashMap<>();
        this.itemStack = null;
        this.customAction = null;
        this.gui = null;
        this.replace = false;
        this.itemType = null;
    }

    public Material getMaterial() {
        return material;
    }

    public ShopItem setMaterial(Material material, Integer count) {
        this.count = count;
        this.material = material;
        return this;
    }

    public TeamPlayer.ItemType getItemType() {
        return itemType;
    }

    public ShopItem setItemType(TeamPlayer.ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public ShopGui getGui() {
        return gui;
    }

    public ShopItem setGui(ShopGui gui) {
        this.gui = gui;
        return this;
    }

    public boolean isReplace() {
        return replace;
    }

    public ShopItem setReplace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public Material getCost() {
        return cost;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public Map<String, Object> getPersistentDataContainer() {
        return persistentDataContainer;
    }

    public ShopItem setCost(Material cost, Integer costCount) {
        this.costCount = costCount;
        this.cost = cost;
        return this;
    }

    public Integer getCostCount() {
        return costCount;
    }


    public Integer getCount() {
        return count;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ShopItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public Component getName() {
        return name;
    }

    public ShopItem setName(Component name) {
        this.name = name;
        return this;
    }

    public List<Component> getLore() {
        return lore;
    }

    public ShopItem setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }
    public ShopItem setLore(Component lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }
    public ShopItem addPercistData(String key, Object value){
        this.persistentDataContainer.put(key, value);
        return this;
    }
    public Map<Enchantment, Integer> getEnchantment() {
        return this.enchantments;
    }
    public ShopItem setEnchantment(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }
    public ShopItem setEnchantment(Enchantment enchantments, Integer lvl) {
        this.enchantments = Map.of(enchantments, lvl);
        return this;
    }
    public ShopItem setCustomAction(IActionButton<ShopItem, Player> action){
        this.customAction = action;
        return this;
    }
    public IActionButton<ShopItem, Player> getCustomAction(){
        return this.customAction;
    }


    public static ShopItem builder(){
        return new ShopItem();
    }
    public GuiItem build(){
        ItemBuilder builder;
        if (getMaterial() != null){
            builder = ItemBuilder.from(getMaterial());
            builder.amount(getCount());
        }else {
            builder = ItemBuilder.from(itemStack);
        }
        builder.name(getName());
        List<Component> loreTemp = getLore();
        builder.lore(Arrays.asList(Component.text("Цена: ", TextColor.fromCSSHexString("#6c5ce7")).append(getTranslatedMaterial(getCost(), getCostCount()))));
        GuiItem guiItem = builder.asGuiItem(this::action);
        return guiItem;
    }

    public void action(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (!hasItemCount(player, getCost(), getCostCount())){
            return;
        }
        if (getMaterial()!=null){
            if (!hasInventorySpaceForItemCount(player, getMaterial(), getCount())){
                return;
            }
        }
        removeItems(player, getCost(), getCostCount());
        if (getCustomAction()!=null){
            getCustomAction().execute(this, player);
            return;
        }
        ItemStack itemToGive = getItemToGive(BPlayer.getByUUID(player.getUniqueId()).getTeamPlayer());
        ItemStack[] contents = player.getInventory().getContents();
        boolean added = false;
        if (getItemType()!=null){
            for (int i = 0; i < contents.length; i++) {
                ItemStack itemStack = contents[i];
                if (itemStack!=null){
                    if (itemStack.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)){
                        if (itemStack.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(getItemType().name())){
                            Logger.debug("replaced: "+getItemType().name());
                            contents[i] = itemToGive;
                            added = true;
                            break;
                        }
                    }
                }
            }
        }
        if (isReplace()){
            if (!added){
                Logger.debug("not added");
                player.getInventory().addItem(itemToGive);
            }else {
                Logger.debug("added");
                player.getInventory().setContents(contents);
            }
        }else {
            player.getInventory().addItem(itemToGive);
        }
        if (getGui()!=null){
            getGui().update();
        }
    }
    public ItemStack getItemToGive(TeamPlayer player){
        ItemStack itemToGive;
        if (getMaterial()!=null){
            itemToGive = new ItemStack(getMaterial(), getCount());
        }else {
            itemToGive = getItemStack();
        }
        ItemMeta itemMeta = itemToGive.getItemMeta();
        if (getItemType()!=null){
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, getItemType().name());
        }
        if (persistentDataContainer!=null){
            for (Map.Entry<String, Object> entry : persistentDataContainer.entrySet()){
                if (entry.getValue() instanceof Integer value){
                    itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.INTEGER, value);
                } else if (entry.getValue() instanceof String value) {
                    itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.STRING, value);
                } else if (entry.getValue() instanceof Boolean value) {
                    itemMeta.getPersistentDataContainer().set(Objects.requireNonNull(NamespacedKey.fromString(entry.getKey())), PersistentDataType.BOOLEAN, value);
                } else {
                    continue;
                }
            }
        }
        if (enchantments != null){
            for (Map.Entry<Enchantment, Integer> enchantment : getEnchantment().entrySet()){
                itemMeta.addEnchant(enchantment.getKey(), enchantment.getValue(),true);
            }
        }
        if (player.getGameTeam().hasUpgrade(TeamUpgrades.SHARPNESS)){
            if (itemMeta.getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)){
                if (itemMeta.getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.SWORD.name())){
                    itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                }
            }
        }
        itemToGive.setItemMeta(itemMeta);
        return itemToGive;
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
    public boolean hasInventorySpaceForItemCount(Player player, Material material, int count) {
        Inventory playerInventory = player.getInventory();

        for (ItemStack itemStack : playerInventory.getContents()) {
            if (itemStack == null) {
                // Found an empty slot, subtract the count and continue checking
                count -= material.getMaxStackSize();
            } else if (itemStack.getType() == material && itemStack.getAmount() < itemStack.getMaxStackSize()) {
                // Found a slot with the same material and not fully stacked
                int spaceAvailable = itemStack.getMaxStackSize() - itemStack.getAmount();
                count -= spaceAvailable;
            }

            if (count <= 0) {
                // There is enough space for the specified count
                return true;
            }
        }

        // Count is still greater than 0, not enough space
        return false;
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
}
