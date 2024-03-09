package me.dalynkaa.spbedwars.commands.arenacommand.subcommands;

import me.dalynkaa.spbedwars.commands.arenacommand.ArenaSubCommand;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CustomSkin;
import org.bukkit.entity.Player;

import java.util.List;

public class downloadSkin extends ArenaSubCommand {

    @Override
    public String getName() {
        return "downloadSkin";
    }

    @Override
    public String getDescription() {
        return "Загрузить скин игрока";
    }

    @Override
    public String getSyntax() {
        return "/arena downloadSkin <name> <url>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 3){
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        String name = args[1];
        String url = args[2];
        player.sendMessage("Скин " + name + " загружается с " + url);
        CustomSkin skin = CustomSkin.getByUrl(url, name);
        player.sendMessage("Скин " + name + " загружен");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            return List.of("<name>");
        }
        if (args.length == 3){
            return List.of("<url>");
        }
        return null;
    }
}
