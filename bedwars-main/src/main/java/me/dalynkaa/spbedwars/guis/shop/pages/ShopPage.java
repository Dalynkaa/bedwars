package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;

public abstract class ShopPage {

    public abstract String getName();
    public abstract ShopCategory getCategory();
    public abstract GuiItem getIcon();
    public abstract void renderPage();
}
