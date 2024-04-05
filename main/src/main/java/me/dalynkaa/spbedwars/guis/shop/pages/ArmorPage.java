package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ArmorPage extends ShopPage {
    ShopGui gui;
    public ArmorPage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Броня";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.ARMOR;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.IRON_BOOTS);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Armor armor = new ItemsForShop.Armor(gui.teamPlayer);
        gui.gui.setItem(19, armor.getStone());
        gui.gui.setItem(20, armor.getIron());
        gui.gui.setItem(21, armor.getDiamond());
    }
}
