package me.dalynkaa.bedwarslobby.commands.serverCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.serverCommand.ServerSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class EditCommand extends ServerSubCommand {
    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "Редактирование сервера";
    }

    @Override
    public String getSyntax() {
        return "/server <serverId> edit <true/false>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length != 3){
            player.sendMessage("Неверное количество аргументов");
            return;
        }
        boolean edit = Boolean.parseBoolean(args[2]);
        UUID serverId = UUID.fromString(args[0]);
        if (!SPBedWarsLobby.getInstance().servers.containsKey(serverId)){
            player.sendMessage("Сервер не найден");
            return;
        }
        ServerRegistrator server = SPBedWarsLobby.getInstance().servers.get(serverId);
        server.setEdit(edit);
        SPBedWarsLobby.getInstance().getProxyUtils().setServerEdit(serverId, edit);
        player.sendMessage("Режим редактирования сервера " + serverId + " установлен на " + edit);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 3){
            return List.of("true", "false");
        }
        return null;
    }
}
