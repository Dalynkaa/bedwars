package me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.GameSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class spectateCommand extends GameSubCommand {
    @Override
    public String getName() {
        return "spectate";
    }

    @Override
    public String getDescription() {
        return "Наблюдать за игрой";
    }

    @Override
    public String getSyntax() {
        return "/game <gameId> spectate";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 2){
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        UUID gameId = UUID.fromString(args[0]);
        GameRegistrator gameRegistrator = null;
        ServerRegistrator server = null;
        for (ServerRegistrator serverRegistrator: SPBedWarsLobby.getInstance().servers.values()) {
            for (GameRegistrator game : serverRegistrator.getGames()) {
                if (game.getGameId().equals(gameId)) {
                    gameRegistrator = game;
                    server = serverRegistrator;
                    break;
                }
            }
        }
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (bPlayer == null){
            player.sendMessage("Ошибка");
            return;
        }
        bPlayer.joinGame(gameRegistrator);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
