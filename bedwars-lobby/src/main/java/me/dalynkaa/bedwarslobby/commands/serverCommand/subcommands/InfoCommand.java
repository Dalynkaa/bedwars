package me.dalynkaa.bedwarslobby.commands.serverCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.serverCommand.ServerSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class InfoCommand extends ServerSubCommand {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Получить информацию о сервере";
    }

    @Override
    public String getSyntax() {
        return "/server <serverId> info";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 2){
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        UUID serverId = UUID.fromString(args[0]);
        if (!SPBedWarsLobby.getInstance().servers.containsKey(serverId)){
            player.sendMessage("Сервер не найден");
            return;
        }
        ServerRegistrator server = SPBedWarsLobby.getInstance().servers.get(serverId);
        player.sendMessage("--------------info----------------");
        player.sendMessage("ServerId: " + server.getServerId());
        player.sendMessage("ServerName: " + server.getServerName());
        player.sendMessage("ArenaType: " + server.getArenaType());
        if (server.getGames() != null){
            player.sendMessage("Games: " + server.getGames().size());
        }else{
            player.sendMessage("Games: 0");
        }
        player.sendMessage("IsEdit: " + server.getEdit());
        player.sendMessage("--------------info----------------");


    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
