package me.dalynkaa.bedwarslobby.commands.menuCommand;

import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            if (bPlayer == null){
                player.sendMessage("Ошибка");
                return true;
            }
            bPlayer.openMenu();
            return false;
        }
        return false;
    }
}
