package me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.GameSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.MessageType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ArenaRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.List;

public class NewCommand extends GameSubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "create new game";
    }

    @Override
    public String getSyntax() {
        return "/game new create <arenaName>";
    }

    @Override
    public void perform(Player player, String[] args) {
        BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
        if (bPlayer == null) {
            player.sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text("You are not registered", TextColor.fromCSSHexString("#d63031"))));
            return;
        }
        if (args.length == 3) {
            String arenaName = args[2];
            ArenaRegistrator arena = ArenaRegistrator.getArenaByName(arenaName);
            if (arena == null) {
                bPlayer.sendMessage("Арена не найдена", MessageType.ERROR);
                return;
            }
            ServerRegistrator server = SPBedWarsLobby.getInstance().servers.get(arena.getServerId());
            server.createGame(arena, bPlayer, ArenaTypes.SOLO);
        } else {
            bPlayer.sendMessage("Неверное количество аргументов", MessageType.ERROR);
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 3) {
            List<String> arenas = SPBedWarsLobby.getInstance().arenas.stream().map(ArenaRegistrator::getArenaName).toList();
            return arenas;
        }
        return null;
    }
}
