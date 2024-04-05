package me.dalynkaa.spbedwars.commands.gameCommand.subcommands;

import me.dalynkaa.spbedwars.commands.gameCommand.GameManager;
import me.dalynkaa.spbedwars.commands.gameCommand.GameSubCommand;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoCommand extends GameSubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Получить информацию о игре";
    }

    @Override
    public String getSyntax() {
        return "/game <gameId> info";
    }

    @Override
    public void perform(Player player, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (bPlayer == null) {
            player.sendMessage("Ваш игрок не найден в базе данных");
            return;
        }
        if (args.length != 2) {
            player.sendMessage("Неверное количество аргументов");
            return;
        }

        BWGame game = GameManager.getGameByArgs(bPlayer, args[0]);
        if (game == null) {
            return;
        }
        player.sendMessage("--------------info----------------");
        player.sendMessage("GameId: " + game.getGameId());
        player.sendMessage("GameStage: " + game.getGameStage());
        player.sendMessage("Players: " + game.getPlayers().size());
        for (BPlayer p : game.getPlayers()) {
            player.sendMessage(" - " + p.getPlayer().getName());
        }
        player.sendMessage("MaxPlayers: " + game.getArena().getArenaType().getPlayers());
        player.sendMessage("Active teams: " + game.getActiveTeams().size());
        for (GameTeam team : game.getActiveTeams()) {
            player.sendMessage(" - " + team.getTeam().getName() + ": " + team.hasActive().toString());
        }
        player.sendMessage("--------------info----------------");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
