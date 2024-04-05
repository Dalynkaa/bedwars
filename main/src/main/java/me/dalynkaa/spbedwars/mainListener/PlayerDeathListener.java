package me.dalynkaa.spbedwars.mainListener;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {
    public PlayerDeathListener(SPBedWars bedWars) {
        bedWars.getServer().getPluginManager().registerEvents(this, bedWars);
    }

    @EventHandler
    public void playerDeathEvent(PlayerRespawnEvent event){
        Player bukkitPlayer = event.getPlayer();
        BPlayer bPlayer = BPlayer.getByUUID(bukkitPlayer.getUniqueId());
        TeamPlayer teamPlayer = bPlayer.getTeamPlayer();
        BWGame game = bPlayer.getGame();
        if (game.getGameStage().equals(GameStage.RUNNING)){
            if (teamPlayer.getGameTeam().hasBed()){
                teamPlayer.respawn(event);
            }else {
                teamPlayer.spectator(event);
            }
            teamPlayer.sendMessage("Вы умерли", MessageType.SUCCESS);
        }
    }
    @EventHandler
    public void playerBedEvent(PlayerSetSpawnEvent event){
        if (event.getCause().equals(PlayerSetSpawnEvent.Cause.BED)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void playerRegenerateEvent(EntityRegainHealthEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            if (bPlayer.getGame() == null){
                return;
            }
            if (bPlayer.getGame().getGameStage().equals(GameStage.RUNNING)){
                event.setAmount(event.getAmount()/4);
            }
        }
    }
}
