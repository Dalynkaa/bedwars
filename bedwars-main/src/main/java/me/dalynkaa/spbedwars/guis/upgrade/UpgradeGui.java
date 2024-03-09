package me.dalynkaa.spbedwars.guis.upgrade;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class UpgradeGui {
    public Gui gui;
    private TeamPlayer t;
    public List<Integer> userSlots = List.of(10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,39,40,41);
    public UpgradeGui(TeamPlayer teamPlayer) {
        gui = Gui.gui()
                .title(Component.text("Улучшения"))
                .rows(6)
                .disableAllInteractions()
                .create();
        gui.setUpdating(true);


        renderPage(teamPlayer);
        gui.open(teamPlayer.getPlayer());
    }
    public void renderPage(TeamPlayer player){
        ItemsForUpgrade itemsForUpgrade = new ItemsForUpgrade(player, this);
        gui.setItem(10, itemsForUpgrade.getSharpnessUpgrade());
        gui.setItem(11, itemsForUpgrade.getProtectionUpgrade());
        GuiItem line = ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem();
        List<Integer> slots = List.of(27,28,29,30,31,32,33,34,35);
        gui.setItem(slots, line);
    }
    public void update(){
        gui.setItem(userSlots, ItemBuilder.from(Material.AIR).asGuiItem());
        renderPage(t);
        gui.update();
    }
}
