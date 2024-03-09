package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class UtilsPage extends ShopPage {
    ShopGui gui;
    public UtilsPage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Разное";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.UTILITIES;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.TNT);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Utils utils = new ItemsForShop.Utils(gui.teamPlayer);
        gui.gui.setItem(19, utils.getGoldApple());
        gui.gui.setItem(20, utils.getEnderPearl());
        gui.gui.setItem(21, utils.getWaterBuket());
        gui.gui.setItem(22, utils.getTnt());
        gui.gui.setItem(23, utils.getFireball());
    }
}
