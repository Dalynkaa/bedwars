package me.dalynkaa.bedwarslobby.commands.settingsCommand.subcommands;

import me.dalynkaa.bedwarslobby.commands.settingsCommand.SettingsSubCommand;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.MessageType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.config.Config;
import org.bukkit.Location;

import java.util.List;

public class SetSpawnPosCommand extends SettingsSubCommand {
    @Override
    public String getName() {
        return "setSpawn";
    }

    @Override
    public String getDescription() {
        return "Set the spawn position for the lobby.";
    }

    @Override
    public String getSyntax() {
        return "/settings setSpawn";
    }

    @Override
    public void perform(BPlayer bPlayer, String[] args) {
        Location location = bPlayer.getPlayer().getLocation();
        Config.setSpawnLocation(location);
        bPlayer.sendMessage("Spawn location set to " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ(), MessageType.SUCCESS);
    }

    @Override
    public List<String> getSubcommandArguments(BPlayer bPlayer, String[] args) {
        return null;
    }
}
