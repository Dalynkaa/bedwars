package me.dalynkaa.bedwarslobby.commands.gameCommand.subcommands;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.commands.gameCommand.GameSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ArenaRegistrator;
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
        return "/game new create";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 3) {
            String arenaName = args[2];
            ArenaRegistrator arena = ArenaRegistrator.getArenaByName(arenaName);
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            if (arena != null) {
                SPBedWarsLobby.getInstance().getProxyUtils().requestGameCreation(arena.getArenaId(), arena.getServerId(), bPlayer);
            } else {
                player.sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text("Arena not found", TextColor.fromCSSHexString("#d63031"))));
            }
        } else {
            player.sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text("Invalid syntax", TextColor.fromCSSHexString("#d63031"))));
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
