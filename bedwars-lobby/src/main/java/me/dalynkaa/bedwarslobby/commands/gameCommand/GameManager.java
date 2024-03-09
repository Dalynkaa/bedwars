package me.dalynkaa.bedwarslobby.commands.gameCommand;


import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands.InfoCommand;
import me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands.joinCommand;
import me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands.spectateCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager implements TabExecutor {

    private final ArrayList<GameSubCommand> subcommands = new ArrayList<>();

    public GameManager(){
        subcommands.add(new InfoCommand());
        subcommands.add(new joinCommand());
        subcommands.add(new spectateCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubCommands().size(); i++){
                    if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(p, args);
                    }
                }
            }else {
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++){
                    p.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }
        }
        return true;
    }
    public ArrayList<GameSubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){// gameId
            ArrayList<String> subcommandsArguments1 = new ArrayList<>();
            for (ServerRegistrator serverRegistrator: SPBedWarsLobby.getInstance().servers.values()){
                for (GameRegistrator gameRegistrator: serverRegistrator.getGames()){
                    if (gameRegistrator.getGameId().toString().toLowerCase().startsWith(args[0].toLowerCase()))
                        subcommandsArguments1.add(gameRegistrator.getGameId().toString());
                }
            }
            return subcommandsArguments1;
        } else if (args.length == 2) { // subcommand
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if(args.length >= 3){ // subcommand arguments
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }
        return null;
    }
}
