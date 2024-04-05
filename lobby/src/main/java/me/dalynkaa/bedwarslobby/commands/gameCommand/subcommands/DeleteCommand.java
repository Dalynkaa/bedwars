package me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.GameSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.game.GameRegistrator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class DeleteCommand extends GameSubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete and unregister game from server";
    }

    @Override
    public String getSyntax() {
        return "/game <gameId> delete";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        UUID gameId = UUID.fromString(args[0]);
        GameRegistrator gameRegistrator = null;
        ServerRegistrator server = null;
        for (ServerRegistrator serverRegistrator : SPBedWarsLobby.getInstance().servers.values()) {
            for (GameRegistrator game : serverRegistrator.getGames()) {
                if (game.getGameId().equals(gameId)) {
                    gameRegistrator = game;
                    server = serverRegistrator;
                }
            }
        }
        if (gameRegistrator == null) {
            player.sendMessage("Игра не найдена");
            return;
        }
        SPBedWarsLobby.getInstance().getProxyUtils().requestGameDelete(gameId, server.getServerId());
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
