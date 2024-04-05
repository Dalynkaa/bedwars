package me.dalynkaa.bedwarslobby.commands.serverCommand;


import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.serverCommand.subcommands.EditCommand;
import me.dalynkaa.bedwarslobby.commands.serverCommand.subcommands.InfoCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerManager implements TabExecutor {

    private final ArrayList<ServerSubCommand> subcommands = new ArrayList<>();

    public ServerManager(){
        subcommands.add(new InfoCommand());
        subcommands.add(new EditCommand());
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
            }else if(args.length == 0){
                SPBedWarsLobby.getInstance().getProxyUtils().requerstServerRegistration();
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++){
                    p.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }
        }
        return true;
    }
    public ArrayList<ServerSubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){// serverId
            ArrayList<String> subcommandsArguments1 = new ArrayList<>();
            for (UUID id: SPBedWarsLobby.getInstance().servers.keySet()){
                if (id.toString().toLowerCase().startsWith(args[0].toLowerCase()))
                    subcommandsArguments1.add(id.toString());
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
