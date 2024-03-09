package me.dalynkaa.spbedwars.guis.shop.pages;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.guis.shop.ShopCategory;
import me.dalynkaa.spbedwars.guis.shop.ShopGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class QuickPage extends ShopPage {
    ShopGui gui;
    public QuickPage(ShopGui gui) {
        this.gui = gui;
    }


    @Override
    public String getName() {
        return "Быстрая закупка";
    }

    @Override
    public ShopCategory getCategory() {
        return ShopCategory.QUICK;
    }

    @Override
    public GuiItem getIcon() {
        ItemBuilder itemBuilder = ItemBuilder.from(Material.SCULK_CATALYST);
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
        ItemsForShop.Melee melee = new ItemsForShop.Melee(gui.teamPlayer);
        ItemsForShop.Potions potions = new ItemsForShop.Potions(gui.teamPlayer);
        ItemsForShop.Tools tools = new ItemsForShop.Tools(gui.teamPlayer, gui);
        ItemsForShop.Ranget ranget = new ItemsForShop.Ranget(gui.teamPlayer);
        ItemsForShop.Armor armor = new ItemsForShop.Armor(gui.teamPlayer);
        ItemsForShop.Utils utils = new ItemsForShop.Utils(gui.teamPlayer);

        gui.gui.setItem(19, blocks.getWool());
        gui.gui.setItem(20, melee.getStone());
        gui.gui.setItem(21, armor.getStone());
        gui.gui.setItem(22, tools.getPickaxe());
        gui.gui.setItem(23, ranget.getNormal_bow());
        gui.gui.setItem(24, potions.getSpeed2());
        gui.gui.setItem(25, utils.getTnt());
        gui.gui.setItem(28, blocks.getPlanks());
        gui.gui.setItem(29, melee.getIron());
        gui.gui.setItem(30, armor.getIron());
        gui.gui.setItem(31, tools.getShears());
        gui.gui.setItem(32, ranget.getArrow());
        gui.gui.setItem(33, potions.getInvincibility());
        gui.gui.setItem(34, utils.getWaterBuket());
        gui.gui.setItem(37, utils.getGoldApple());
        gui.gui.setItem(38, potions.getJump5());
        gui.gui.setItem(39, blocks.getGlass());
        gui.gui.setItem(40, blocks.getEnd_stone());
        gui.gui.setItem(41, tools.getAxe());
        gui.gui.setItem(42, ranget.getPower_bow());
        gui.gui.setItem(43, ranget.getPower_punch_bow());



    }
}
