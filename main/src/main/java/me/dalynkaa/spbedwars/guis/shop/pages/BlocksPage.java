package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class BlocksPage extends ShopPage {
    ShopGui gui;
    public BlocksPage(ShopGui gui) {
        this.gui = gui;
    }
    @Override
    public String getName() {
        return "Блоки";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.BLOCKS;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.TERRACOTTA);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Blocks blocks = new ItemsForShop.Blocks(gui.teamPlayer);
        gui.gui.setItem(19, blocks.getWool());
        gui.gui.setItem(20, blocks.getTeracotta());
        gui.gui.setItem(21, blocks.getGlass());
        gui.gui.setItem(22, blocks.getEnd_stone());
        gui.gui.setItem(23, blocks.getLeader());
        gui.gui.setItem(24, blocks.getPlanks());
        gui.gui.setItem(25, blocks.getObsidian());
    }
}
