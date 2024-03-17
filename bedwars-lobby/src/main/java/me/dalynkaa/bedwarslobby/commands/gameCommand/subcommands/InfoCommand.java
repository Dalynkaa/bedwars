package me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.GameSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

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
        if (args.length != 2) {
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        UUID gameId = UUID.fromString(args[0]);
        for (ServerRegistrator serverRegistrator : SPBedWarsLobby.getInstance().servers.values()) {
            for (GameRegistrator gameRegistrator : serverRegistrator.getGames()) {
                if (gameRegistrator.getGameId().equals(gameId)) {
                    player.sendMessage("--------------info----------------");
                    player.sendMessage("GameId: " + gameRegistrator.getGameId());
                    player.sendMessage("GameName: " + gameRegistrator.getArenaName());
                    player.sendMessage("GameStage: " + gameRegistrator.getGameStage());
                    player.sendMessage("Players: " + gameRegistrator.getPlayers().size());
                    for (BPlayer p : gameRegistrator.getPlayers()) {
                        player.sendMessage(" - " + p.getOPlayer().getName());
                    }
                    player.sendMessage("MaxPlayers: " + gameRegistrator.getGameType().getPlayers());
                    player.sendMessage("Active teams: " + gameRegistrator.getActivePlayers().size());
                    for (BPlayer team : gameRegistrator.getActivePlayers()) {
                        player.sendMessage(" - " + team.getOPlayer().getName());
                    }
                    player.sendMessage("--------------info----------------");
                    return;
                }
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
