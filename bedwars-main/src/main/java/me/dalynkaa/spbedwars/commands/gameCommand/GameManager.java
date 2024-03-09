package me.dalynkaa.spbedwars.commands.gameCommand;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.gameCommand.subcommands.DestroyBed;
import me.dalynkaa.spbedwars.commands.gameCommand.subcommands.InfoCommand;
import me.dalynkaa.spbedwars.commands.gameCommand.subcommands.KillTeam;
import me.dalynkaa.spbedwars.commands.gameCommand.subcommands.MakeWinner;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameManager implements TabExecutor {

    private final ArrayList<GameSubCommand> subcommands = new ArrayList<>();

    public GameManager() {
        subcommands.add(new DestroyBed());
        subcommands.add(new KillTeam());
        subcommands.add(new MakeWinner());
        subcommands.add(new InfoCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(p, args);
                    }
                }
            } else if (args.length == 0) {
                p.sendMessage("--------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++) {
                    p.sendMessage(getSubCommands().get(i).getSyntax() + " - " + getSubCommands().get(i).getDescription());
                }
                p.sendMessage("--------------------------------");
            }
        }
        return true;
    }

    public ArrayList<GameSubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {// gameId
            ArrayList<String> subcommandsArguments1 = new ArrayList<>();
            subcommandsArguments1.add("current");
            for (BWGame game : SPBedWars.getInstance().activeGames.values()) {
                if (game.getGameId().toString().toLowerCase().startsWith(args[0].toLowerCase())) {
                    subcommandsArguments1.add(game.getGameId().toString());
                }
            }
            return subcommandsArguments1;
        } else if (args.length == 2) { // subcommand
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 3) { // subcommand arguments
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[1].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }
        return null;
    }

    public static BWGame getGameByArgs(BPlayer player, String id) {
        if (Objects.equals(id, "current")) {
            if (player.getCurrentGame() == null) {
                player.sendMessage("Вы не находитесь в игре", MessageType.ERROR);
                return null;
            }
            id = player.getCurrentGame().toString();
        }
        BWGame game = SPBedWars.getInstance().activeGames.get(UUID.fromString(id));
        if (game == null) {
            player.sendMessage("Игра не найдена", MessageType.ERROR);
            return null;
        }
        return game;
    }
}
