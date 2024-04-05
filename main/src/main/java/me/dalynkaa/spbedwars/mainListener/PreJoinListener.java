package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.usableClasses.InventoryButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PreJoinListener implements Listener {

    private SPBedWars main;
    public PreJoinListener(SPBedWars main){
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void preJoin(AsyncPlayerPreLoginEvent event){
        BPlayer bPlayer = BPlayer.getByUUID(event.getUniqueId());
        if (bPlayer == null){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Ошибка при получении пользователя\nПожалуйста перезайдите через 5с.\nИли свяжитесь с администрацией для решения проблемы"));
            return;
        }
        if (Config.getServerEdit()){
            if (Bukkit.getOfflinePlayer(event.getUniqueId()).isOp()){
                return;
            }
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Сервер находится в режиме редактирования\nПожалуйста перезайдите через 5с.\nИли свяжитесь с администрацией для решения проблемы"));
        }
        if (!SPBedWars.getInstance().gameJoinTemp.containsKey(event.getUniqueId())){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("Вы не выбрали игру\nПожалуйста перезайдите через 5с.\nИли свяжитесь с администрацией для решения проблемы"));
            return;
        }
    }
    @EventHandler
    public void joinListener(PlayerJoinEvent event){
        event.getPlayer().getInventory().clear();
        ItemStack backToLobby = InventoryButton.from(Material.PAPER)
                .setName(Component.text("Вернуться в лобби"))
                .setLore(Component.text("Вернуться в лобби"))
                .build((interactEvent) -> {
                    BPlayer player = BPlayer.getByUUID(interactEvent.getPlayer().getUniqueId());
                    player.sendToLobby();
                }, "backToLobby");
        event.getPlayer().getInventory().setItem(8, backToLobby);
    }
}
