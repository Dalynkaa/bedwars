package me.dalynkaa.bedwarslobby.utils;

import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.usable.InventoryButton;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ControllItems {
    private static ItemStack player_button;

    public ControllItems() {
        player_button = InventoryButton.from(Material.RECOVERY_COMPASS)
                .setName(Component.text("Меню", TextColor.fromCSSHexString("#00cec9")))
                .setLore(Arrays.asList(Component.text("Открывает игровое меню", TextColor.fromCSSHexString("#a29bfe"))))
                .build((event -> {
                    Player player = event.getPlayer();
                    BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
                    bPlayer.openMenu();
                }), "menu_item");
    }

    public void giveItems(BPlayer bPlayer) {
        Player player = bPlayer.getPlayer();
        if (player == null) {
            return;
        }
        player.getInventory().clear();
        player.getInventory().setItem(4, player_button);
    }
}
