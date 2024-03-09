package me.dalynkaa.spbedwars.guis.upgrade.item;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.upgrade.UpgradeGui;
import me.dalynkaa.spbedwars.guis.upgrade.upgradeAction.IUpgradeActionButton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class UpgradeItem {
    private ItemStack itemStack;
    private Material material;
    private Integer costCount;
    private Component name;
    private List<Component> lore;
    private IUpgradeActionButton<UpgradeItem, Player> customAction;
    private UpgradeGui gui;

    public UpgradeItem(ItemStack item){
        this.name = null;
        this.lore = null;
        this.material = null;
        this.costCount = null;
        this.itemStack = item;
        this.customAction = null;
        this.gui = null;
    }
    public UpgradeItem(){
        this.name = null;
        this.lore = null;
        this.material = null;
        this.costCount = null;
        this.itemStack = null;
        this.customAction = null;
        this.gui = null;
    }

    public Material getMaterial() {
        return material;
    }

    public UpgradeItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public UpgradeGui getGui() {
        return gui;
    }

    public UpgradeItem setGui(UpgradeGui gui) {
        this.gui = gui;
        return this;
    }

    public UpgradeItem setCost(Integer costCount) {
        this.costCount = costCount;
        return this;
    }

    public Integer getCostCount() {
        return costCount;
    }


    public ItemStack getItemStack() {
        return itemStack;
    }

    public UpgradeItem setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public Component getName() {
        return name;
    }

    public UpgradeItem setName(Component name) {
        this.name = name;
        return this;
    }

    public List<Component> getLore() {
        return lore;
    }

    public UpgradeItem setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }
    public UpgradeItem setLore(Component lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }
    public UpgradeItem setCustomAction(IUpgradeActionButton<UpgradeItem, Player> action){
        this.customAction = action;
        return this;
    }
    public IUpgradeActionButton<UpgradeItem, Player> getCustomAction(){
        return this.customAction;
    }


    public static UpgradeItem builder(){
        return new UpgradeItem();
    }
    public GuiItem build(){
        ItemBuilder builder;
        if (getMaterial() != null){
            builder = ItemBuilder.from(getMaterial());
        }else {
            builder = ItemBuilder.from(itemStack);
        }
        builder.name(getName());
        List<Component> loreTemp = getLore();
        builder.lore(Arrays.asList(Component.text("Цена: ", TextColor.fromCSSHexString("#6c5ce7")).append(getTranslatedMaterial(Material.DIAMOND, getCostCount()))));
        GuiItem guiItem = builder.asGuiItem(this::action);
        return guiItem;
    }

    public void action(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (!hasItemCount(player, Material.DIAMOND, getCostCount())){
            return;
        }
        removeItems(player, Material.DIAMOND, getCostCount());
        getCustomAction().execute(this, player);
        if (gui != null){
            gui.update();
        }
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
}
