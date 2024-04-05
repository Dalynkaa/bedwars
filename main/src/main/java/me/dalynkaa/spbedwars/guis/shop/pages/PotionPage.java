package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class PotionPage extends ShopPage {
    ShopGui gui;
    public PotionPage(ShopGui shopGui) {
        this.gui = shopGui;
    }

    @Override
    public String getName() {
        return "Зелья";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.POTIONS;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.BREWING_STAND);
        itemBuilder.name(Component.text(getName()));
        GuiItem guiItem = itemBuilder.asGuiItem((event -> {
            gui.update(getCategory());
        }));
        return guiItem;
    }

    @Override
    public void renderPage() {
        gui.gui.updateTitle(getName());
        ItemsForShop.Potions potions = new ItemsForShop.Potions(gui.teamPlayer);
        gui.gui.setItem(19, potions.getSpeed2());
        gui.gui.setItem(20, potions.getJump5());
        gui.gui.setItem(21, potions.getInvincibility());
    }
}
