package me.dalynkaa.spbedwars.commands.arenacommand.subcommands;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.arenacommand.ArenaSubCommand;
import me.dalynkaa.spbedwars.utils.config.SkinConfig;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CustomSkin;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameLocation;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameShop;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameShopType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class createShop extends ArenaSubCommand {
    @Override
    public String getName() {
        return "createShop";
    }

    @Override
    public String getDescription() {
        return "Создать магазин";
    }

    @Override
    public String getSyntax() {
        return "/arena createShop <type> <skin>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (SPBedWars.getInstance().currentCreation == null) {
            player.sendMessage("Сначала создайте арену");
            return;
        }
        if (args.length != 3) {
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        GameShopType gameShopType = GameShopType.valueOf(args[1]);
        if (gameShopType == null) {
            player.sendMessage("Тип магазина не найден");
            return;
        }
        CustomSkin customSkin = CustomSkin.getSkin(args[2]);
        if (customSkin == null) {
            player.sendMessage("Скин не найден");
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        Integer name = null;
        if (itemStack.getType() != Material.AIR) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemStack.getType().equals(Material.CARVED_PUMPKIN)) {
                name = itemMeta.getCustomModelData();
            }
        }

        GameLocation gameLocation = GameLocation.fromLocation(player.getLocation());
        GameShop gameShop = new GameShop(gameLocation, GameShopType.UPGRADE, customSkin.getName(), name);
        SPBedWars.getInstance().currentCreation.getArena().getGameShops().add(gameShop);
        gameShop.spawn();
        player.sendMessage("Магазин создан");
    }


    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            return GameShopType.getNames();
        }
        if (args.length == 3) {
            ArrayList<String> subcommandsArguments1 = new ArrayList<>();
            for (String skin : SkinConfig.getAllSkinsConfig()) {
                if (skin.toLowerCase().startsWith(args[2].toLowerCase())) {
                    subcommandsArguments1.add(skin);
                }
            }
        }
        return null;
    }
}
