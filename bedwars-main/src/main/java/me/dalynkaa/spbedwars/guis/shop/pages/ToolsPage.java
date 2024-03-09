package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ToolsPage extends ShopPage {
    ShopGui gui;
    public ToolsPage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Инструменты";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.TOOLS;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.STONE_PICKAXE);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> gui.update(getCategory())));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Tools tools = new ItemsForShop.Tools(gui.teamPlayer, gui);
        gui.gui.setItem(19, tools.getShears());
        gui.gui.setItem(20, tools.getPickaxe());
        gui.gui.setItem(21, tools.getAxe());
    }
}
