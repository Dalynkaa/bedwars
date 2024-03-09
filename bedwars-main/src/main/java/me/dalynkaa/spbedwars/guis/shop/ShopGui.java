package me.dalynkaa.spbedwars.guis.shop;


import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.guis.shop.pages.*;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import me.dalynkaa.spbedwars.guis.shop.pages.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.*;

public class ShopGui {
    public Gui gui;
    public TeamPlayer teamPlayer;
    public Map<Integer, ShopPage> shopPageMap = new HashMap<>();
    public List<Integer> userSlots = Arrays.asList(18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53);
    public ShopCategory category = ShopCategory.QUICK;
    public ShopGui(TeamPlayer teamPlayer){
        gui = Gui.gui()
                .title(Component.text("Магазин предметов"))
                .rows(6)
                .disableAllInteractions()
                .create();
        this.teamPlayer = teamPlayer;
        gui.setUpdating(true);
        shopPageMap.put(ShopCategory.QUICK.getId(), new QuickPage(this));
        shopPageMap.put(ShopCategory.BLOCKS.getId(), new BlocksPage(this));
        shopPageMap.put(ShopCategory.MELEE.getId(), new MeleePage(this));
        shopPageMap.put(ShopCategory.ARMOR.getId(), new ArmorPage(this));
        shopPageMap.put(ShopCategory.TOOLS.getId(), new ToolsPage(this));
        shopPageMap.put(ShopCategory.RANGET.getId(), new RangetPage(this));
        shopPageMap.put(ShopCategory.POTIONS.getId(), new PotionPage(this));
        shopPageMap.put(ShopCategory.UTILITIES.getId(), new UtilsPage(this));
        shopPageMap.put(ShopCategory.ANOTHER.getId(), new AnotherPage(this));
        BWGame game = teamPlayer.getGame();
        for (Map.Entry<Integer, ShopPage> entry: shopPageMap.entrySet()) {
            gui.setItem(entry.getKey(), entry.getValue().getIcon());
        }
        gui.setCloseGuiAction(event -> {
            if (SPBedWars.getInstance().shopGuiMap.containsKey(teamPlayer.getUuid())){
                SPBedWars.getInstance().shopGuiMap.remove(teamPlayer.getUuid());
            }
        });
        gui.setOpenGuiAction(event -> {
            if (SPBedWars.getInstance().shopGuiMap.containsKey(teamPlayer.getUuid())){
                SPBedWars.getInstance().shopGuiMap.remove(teamPlayer.getUuid());
            }
            SPBedWars.getInstance().shopGuiMap.put(teamPlayer.getUuid(), this);
        });
        update(ShopCategory.QUICK);
        gui.open(teamPlayer.getPlayer());
    }
    public void update(ShopCategory category){
        this.category = category;
        gui.setItem(userSlots, ItemBuilder.from(Material.AIR).asGuiItem());
        shopPageMap.get(category.getId()).renderPage();
        for (Integer i: ShopCategory.getCursors()){
            if (i.equals(category.getCursor())){
                gui.setItem(i, ItemBuilder.from(Material.LIME_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());
            }else {
                gui.setItem(i, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());
            }
        }
        gui.update();
    }
    public void update(){
        gui.setItem(userSlots, ItemBuilder.from(Material.AIR).asGuiItem());
        shopPageMap.get(this.category.getId()).renderPage();
        for (Integer i: ShopCategory.getCursors()){
            if (i.equals(this.category.getCursor())){
                gui.setItem(i, ItemBuilder.from(Material.LIME_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());
            }else {
                gui.setItem(i, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem());
            }
        }
        gui.update();
    }
}
