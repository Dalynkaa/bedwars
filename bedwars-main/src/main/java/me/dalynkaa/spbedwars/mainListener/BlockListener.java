package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class BlockListener implements Listener {
    List<Material> BED_BLOCKS;
    public BlockListener(SPBedWars main){
        main.getServer().getPluginManager().registerEvents(this, main);
        BED_BLOCKS = Arrays.asList(Material.RED_BED, Material.LIME_BED, Material.BLUE_BED, Material.YELLOW_BED);
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event){
        TeamPlayer player = BPlayer.getByUUID(event.getPlayer().getUniqueId()).getTeamPlayer();
        //TODO: check if block placed in arena box
        BWGame game = player.getGame();
        if (game.getGameStage().equals(GameStage.RUNNING)){
            player.placeBlock(event);
        }

    }
    @EventHandler
    public void blockBreacEvent(BlockBreakEvent event){
        TeamPlayer player = BPlayer.getByUUID(event.getPlayer().getUniqueId()).getTeamPlayer();
        //TODO: check if block placed in arena box
        BWGame game = player.getGame();
        if (game.getGameStage().equals(GameStage.RUNNING)){
            if (BED_BLOCKS.contains(event.getBlock().getType())){
                for (GameTeam team: game.getTeamsInGame()){
                    if (team.getBedPos().getBedPos1().getLocation().getBlock().getType().equals(event.getBlock().getType())){
                        if (player.getGameTeam().getTeam().equals(team.getTeam())){
                            event.setCancelled(true);
                            return;
                        }
                        team.breakBed(game);
                    }
                }
                event.setDropItems(false);
                event.setCancelled(true);
                return;
            }
            player.breakBlock(event);
        }
    }
}
