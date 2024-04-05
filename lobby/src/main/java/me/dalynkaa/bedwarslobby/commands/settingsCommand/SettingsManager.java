package me.dalynkaa.bedwarslobby.commands.settingsCommand;


import me.dalynkaa.bedwarslobby.commands.settingsCommand.subcommands.SetSpawnPosCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager implements TabExecutor {

    private final ArrayList<SettingsSubCommand> subcommands = new ArrayList<>();

    public SettingsManager() {
        subcommands.add(new SetSpawnPosCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            BPlayer bPlayer = BPlayer.getByUUID(p.getUniqueId());
            if (bPlayer == null) return true;
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(bPlayer, args);
                    }
                }
            } else {
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++) {
                    p.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }
        }
        return true;
    }

    public ArrayList<SettingsSubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(((Player) sender).getUniqueId());
        if (bPlayer == null) return null;
        if (args.length == 1) { // subcommand
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 2) { // subcommand arguments
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments(bPlayer, args);
                }
            }
        }
        return null;
    }
}
