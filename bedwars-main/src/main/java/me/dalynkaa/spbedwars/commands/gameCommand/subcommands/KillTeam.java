package me.dalynkaa.spbedwars.commands.gameCommand.subcommands;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.commands.gameCommand.GameManager;
import me.dalynkaa.spbedwars.commands.gameCommand.GameSubCommand;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KillTeam extends GameSubCommand {
    @Override
    public String getName() {
        return "killTeam";
    }

    @Override
    public String getDescription() {
        return "Убить команду";
    }

    @Override
    public String getSyntax() {
        return "/game <gameId> killTeam <teamName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (bPlayer == null) {
            player.sendMessage("Ваш игрок не найден в базе данных");
            return;
        }
        if (args.length != 3) {
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        BWGame game = GameManager.getGameByArgs(bPlayer, args[0]);
        if (game == null) {
            return;
        }
        Teams team = Teams.valueOf(args[2]);
        if (team == null) {
            player.sendMessage("Команда не найдена");
            return;
        }
        for (GameTeam gameTeam : game.getActiveTeams()) {
            if (gameTeam.getTeam().equals(team)) {
                for (TeamPlayer teamPlayer : gameTeam.getTeamPlayers()) {
                    teamPlayer.getPlayer().setHealth(0);
                }
                player.sendMessage("Команда " + team.name() + " убита");
                return;
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 3) {
            ArrayList<String> subcommandsArguments1 = new ArrayList<>();
            subcommandsArguments1.add("current");
            for (Teams teams : Teams.values()) {
                if (teams.name().toLowerCase().startsWith(args[2].toLowerCase())) {
                    subcommandsArguments1.add(teams.name());
                }
            }
        }
        return null;
    }
}
