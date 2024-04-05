package me.dalynkaa.spbedwars.utils.dataclasses.player;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.guis.shop.ItemsForShop;
import me.dalynkaa.spbedwars.infoServices.scoreboard.ScoreboardInit;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameLocation;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.usableClasses.GameUtils;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class TeamPlayer extends BPlayer {

    public TeamPlayer(UUID uuid) {
        super(uuid);
    }

    public TeamPlayer(BPlayer bPlayer) {
        super(bPlayer.getUuid(), bPlayer.getEditArena(), bPlayer.getPreviusGame(), bPlayer.getCurrentGame());

    }

    public GameTeam getGameTeam() {
        BWGame game = getGame();
        if (game == null) {
            return null;
        }
        for (GameTeam gameTeam : game.getTeamsInGame()) {
            for (TeamPlayer teamPlayer : gameTeam.getTeamPlayers()) {
                if (teamPlayer.getUuid().equals(getUuid())) {
                    return gameTeam;
                }
            }
        }
        //Logger.debug("TeamPlayer.getGameTeam() returned null");
        return null;
    }

    public void spawn() {
        GameLocation location = getGameTeam().getSpawn();
        getPlayer().teleport(location.getLocation());
        getPlayer().setGameMode(GameMode.SURVIVAL);
        getPlayer().getInventory().clear();
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack sword = new ItemStack(Material.WOODEN_SWORD);

        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        ItemMeta swordMeta = sword.getItemMeta();

        helmetMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        helmetMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.ARMOR.name());
        chestplateMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        chestplateMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.ARMOR.name());
        leggingsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        leggingsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.ARMOR.name());
        bootsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        bootsMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.ARMOR.name());
        swordMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        swordMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.SWORD.name());

        Color color = getGameTeam().getTeam().getDyeColor().getColor();
        helmetMeta.setColor(color);
        chestplateMeta.setColor(color);
        leggingsMeta.setColor(color);
        bootsMeta.setColor(color);

        helmet.setItemMeta(helmetMeta);
        chestplate.setItemMeta(chestplateMeta);
        leggings.setItemMeta(leggingsMeta);
        boots.setItemMeta(bootsMeta);
        sword.setItemMeta(swordMeta);

        getPlayer().getInventory().setHelmet(helmet);
        getPlayer().getInventory().setChestplate(chestplate);
        getPlayer().getInventory().setLeggings(leggings);
        getPlayer().getInventory().setBoots(boots);
        getPlayer().getInventory().setItem(0, sword);

        sendMessage("Игра началась!", MessageType.SUCCESS);
    }

    /**
     * Respawn the player and set their game mode to survival.
     *
     * @param event The PlayerRespawnEvent triggered when the player respawns
     */
    public void respawn(PlayerRespawnEvent event) {
        GameLocation spectatorLocation = getGame().getArena().getSpectatorLocation();
        GameLocation teamLocation = getGameTeam().getSpawn();
        event.setRespawnLocation(spectatorLocation.getLocation());
        ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.getPersistentDataContainer().set(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN, true);
        swordMeta.getPersistentDataContainer().set(NamespacedKey.fromString("type"), PersistentDataType.STRING, ItemType.SWORD.name());
        sword.setItemMeta(swordMeta);
        getPlayer().getInventory().setItem(0, sword);
        clearInventory();
        getPlayer().setGameMode(GameMode.SPECTATOR);
        GameUtils.startPlayerTimer(this, 5, "До возрождения", (game) -> {
            getPlayer().teleport(teamLocation.getLocation());
            getPlayer().setGameMode(GameMode.SURVIVAL);
            sendMessage("Вы возродились!", MessageType.SUCCESS);
        });
    }

    public void clearInventory() {
        Inventory inventory = getPlayer().getInventory();
        ItemsForShop.Tools tools = new ItemsForShop.Tools();
        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                continue;
            }
            if (item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("keep"), PersistentDataType.BOOLEAN)) {
                if (item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("upgrade"), PersistentDataType.BOOLEAN)) {
                    inventory.remove(item);
                    ItemType type = ItemType.valueOf(item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("type"), PersistentDataType.STRING));
                    inventory.addItem(tools.getToGiveByLvl(1, type, this));
                    continue;
                }
                continue;
            }
            inventory.remove(item);
        }
    }

    /**
     * Teleports the player to the spectator location and sets their game mode to spectator.
     * Sends an error message notifying the player that they have been eliminated.
     *
     * @param event The PlayerRespawnEvent that triggered this method.
     */
    public void spectator(PlayerRespawnEvent event) {
        GameLocation location = getGame().getArena().getSpectatorLocation();
        event.setRespawnLocation(location.getLocation());
        getPlayer().setGameMode(GameMode.SPECTATOR);
        clearInventory();
        if (getGameTeam().getTeamPlayers().size() - 1 <= 0) {
            getGameTeam().setActive(false);
        }
        Logger.debug(getGame().getTeamsInGame().toString());
        getGameTeam().removeTeamPlayer(this);
        if (getGame().getActiveTeams().size() == 1) {
            GameTeam lastTeam = getGame().getActiveTeams().get(0);
            getGame().endGame(lastTeam);
        }
        sendMessage("Вы выбыли!", MessageType.ERROR);
        new ScoreboardInit(SPBedWars.getInstance(), this, GameStage.RUNNING);
    }

    public void placeBlock(BlockPlaceEvent event) {
        getGame().addBlockToList(event.getBlockPlaced());
    }

    public void breakBlock(BlockBreakEvent event) {
        if (!getGame().getPlacedBlocks().contains(event.getBlock())) {
            event.setCancelled(true);
            return;
        }
        getGame().removeBlockFromList(event.getBlock());
    }

    public boolean canBreakBlock(Block block) {
        if (!getGame().getPlacedBlocks().contains(block)) {
            return true;
        }
        return false;
    }

    public void breakBlock(Block block) {
        if (!getGame().getPlacedBlocks().contains(block)) {
            return;
        }
        getGame().removeBlockFromList(block);
    }

    public boolean isLoose() {
        return getGameTeam().getTeamPlayers().size() <= 0;
    }


    public static TeamPlayer fromPlayer(BPlayer player) {
        return new TeamPlayer(player);
    }

    public enum ItemType {
        SWORD,
        PICKAXE,
        AXE,
        SHOVEL,
        BOW,
        ARROW,
        BLOCK,
        FOOD,
        POTION,
        ARMOR,
        OTHER
    }

}
