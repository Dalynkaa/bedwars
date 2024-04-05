package me.dalynkaa.spbedwars.commands.bedwars.subcommands;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.bedwars.BedWarsSubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class reloadCommand extends BedWarsSubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Перезагрузить плагин";
    }

    @Override
    public String getSyntax() {
        return "/bedwars reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("Перезагрузка плагина...");
        SPBedWars.getInstance().initConfig();
        player.sendMessage("Плагин перезагружен");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
