package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class MeleePage extends ShopPage {
    ShopGui gui;
    public MeleePage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Оружия";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.MELEE;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.GOLDEN_SWORD);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Melee melee = new ItemsForShop.Melee(gui.teamPlayer);
        gui.gui.setItem(19, melee.getStone());
        gui.gui.setItem(20, melee.getIron());
        gui.gui.setItem(21, melee.getDiamond());
        gui.gui.setItem(22, melee.getStick());
    }
}
