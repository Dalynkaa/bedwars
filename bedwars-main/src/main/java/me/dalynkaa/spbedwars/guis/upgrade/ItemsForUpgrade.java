package me.dalynkaa.spbedwars.guis.upgrade;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import me.dalynkaa.spbedwars.guis.upgrade.item.UpgradeItem;
import me.dalynkaa.spbedwars.guis.upgrade.upgradeAction.IUpgradeActionButton;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.TeamUpgrades;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemsForUpgrade {
    private TeamPlayer player;
    private UpgradeGui gui;
    private UpgradeItem sharpnessUpgrade;
    private UpgradeItem protectionUpgrade1;
    private UpgradeItem protectionUpgrade2;
    private UpgradeItem protectionUpgrade3;
    private UpgradeItem protectionUpgrade4;

    public ItemsForUpgrade(TeamPlayer player, UpgradeGui gui) {
        this.player = player;
        this.gui = gui;
        // Sharpness upgrade
        sharpnessUpgrade = UpgradeItem.builder().setMaterial(Material.IRON_SWORD).setCost(TeamUpgrades.SHARPNESS.getCostAmount()).setName(Component.text(TeamUpgrades.SHARPNESS.getName())).setCustomAction((upgradeItem, whoClicked) -> {
            whoClicked.sendMessage("Sharpness upgrade");
            player.getGameTeam().addUpgrade(TeamUpgrades.SHARPNESS);
            GameTeam team = player.getGameTeam();
            for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
                teamPlayer.getPlayer().sendMessage("Ваша команда купила улучшение Острота");
                ItemStack[] contents = teamPlayer.getPlayer().getInventory().getContents();
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)) {
                        if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.SWORD.name())) {
                            item.addEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1);
                            teamPlayer.getPlayer().getInventory().setItem(i, item);
                        }
                    }
                }
            }
        }).setGui(gui);
        // End of sharpness upgrade
        protectionUpgrade1 = protectionItem(TeamUpgrades.REINFORCED_ARMOR1.getCostAmount(), "I", (upgradeItem, whoClicked) -> {
            whoClicked.sendMessage("Protection upgrade");
            player.getGameTeam().addUpgrade(TeamUpgrades.REINFORCED_ARMOR1);
            GameTeam team = player.getGameTeam();
            for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
                teamPlayer.getPlayer().sendMessage("Ваша команда купила улучшение Защита I");
                ItemStack[] contents = teamPlayer.getPlayer().getInventory().getContents();
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)) {
                        if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.ARMOR.name())) {
                            item.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                            teamPlayer.getPlayer().getInventory().setItem(i, item);
                        }
                    }
                }
            }
        });
        protectionUpgrade2 = protectionItem(TeamUpgrades.REINFORCED_ARMOR2.getCostAmount(), "II", (upgradeItem, whoClicked) -> {
            whoClicked.sendMessage("Protection upgrade");
            player.getGameTeam().addUpgrade(TeamUpgrades.REINFORCED_ARMOR2);
            GameTeam team = player.getGameTeam();
            for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
                teamPlayer.getPlayer().sendMessage("Ваша команда купила улучшение Защита II");
                ItemStack[] contents = teamPlayer.getPlayer().getInventory().getContents();
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)) {
                        if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.ARMOR.name())) {
                            item.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                            teamPlayer.getPlayer().getInventory().setItem(i, item);
                        }
                    }
                }
            }
        });
        protectionUpgrade3 = protectionItem(TeamUpgrades.REINFORCED_ARMOR3.getCostAmount(), "III", (upgradeItem, whoClicked) -> {
            whoClicked.sendMessage("Protection upgrade");
            player.getGameTeam().addUpgrade(TeamUpgrades.REINFORCED_ARMOR3);
            GameTeam team = player.getGameTeam();
            for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
                teamPlayer.getPlayer().sendMessage("Ваша команда купила улучшение Защита III");
                ItemStack[] contents = teamPlayer.getPlayer().getInventory().getContents();
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)) {
                        if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.ARMOR.name())) {
                            item.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            teamPlayer.getPlayer().getInventory().setItem(i, item);
                        }
                    }
                }
            }
        });
        protectionUpgrade4 = protectionItem(TeamUpgrades.REINFORCED_ARMOR4.getCostAmount(), "IV", (upgradeItem, whoClicked) -> {
            whoClicked.sendMessage("Protection upgrade");
            player.getGameTeam().addUpgrade(TeamUpgrades.REINFORCED_ARMOR4);
            GameTeam team = player.getGameTeam();
            for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
                teamPlayer.getPlayer().sendMessage("Ваша команда купила улучшение Защита IV");
                ItemStack[] contents = teamPlayer.getPlayer().getInventory().getContents();
                for (int i = 0; i < contents.length; i++) {
                    ItemStack item = contents[i];
                    if (item != null && item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("type"), PersistentDataType.STRING)) {
                        if (item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING).equals(TeamPlayer.ItemType.ARMOR.name())) {
                            item.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                            teamPlayer.getPlayer().getInventory().setItem(i, item);
                        }
                    }
                }
            }
        });
        // Protection upgrade
    }
    private UpgradeItem protectionItem(Integer cost, String lvl, IUpgradeActionButton<UpgradeItem, Player> actionButton){
        UpgradeItem upgradeItem = UpgradeItem.builder()
                .setMaterial(Material.IRON_CHESTPLATE)
                .setCost(cost)
                .setGui(gui)
                .setCustomAction(actionButton)
                .setName(Component.text("Защита "+lvl));
        return upgradeItem;
    }
    public GuiItem getSharpnessUpgrade(){
        if (player.getGameTeam().hasUpgrade(TeamUpgrades.SHARPNESS)){
            return ItemBuilder.from(Material.BARRIER).name(Component.text("Уже куплено")).asGuiItem();
        }else {
            return sharpnessUpgrade.build();
        }
    }
    public GuiItem getProtectionUpgrade(){
        if (player.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR4)){
            return ItemBuilder.from(Material.BARRIER).name(Component.text("Уже куплено")).asGuiItem();
        }else if (player.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR3)) {
            return protectionUpgrade4.build();
        }else if (player.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR2)) {
            return protectionUpgrade3.build();
        }else if (player.getGameTeam().hasUpgrade(TeamUpgrades.REINFORCED_ARMOR1)) {
            return protectionUpgrade2.build();
        }else {
            return protectionUpgrade1.build();
        }
    }

}
