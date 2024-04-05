package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class RangetPage extends ShopPage {
    ShopGui gui;
    public RangetPage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Луки";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.RANGET;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.BOW);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Ranget ranget = new ItemsForShop.Ranget(gui.teamPlayer);
        gui.gui.setItem(19, ranget.getArrow());
        gui.gui.setItem(20, ranget.getNormal_bow());
        gui.gui.setItem(21, ranget.getPower_bow());
        gui.gui.setItem(22, ranget.getPower_punch_bow());
    }
}
