package me.dalynkaa.spbedwars.mainListener;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.another.CuboidHighlighter;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameArena;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameSpawner;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.CreationStage;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AllTickListeners implements Listener {

    public AllTickListeners(SPBedWars main){
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void tickListener(ServerTickEndEvent event){
        for (CuboidHighlighter highlighter : SPBedWars.getInstance().particles){
            highlighter.spawnParticle();
        }
        for (BWGame game: SPBedWars.getInstance().activeGames.values()){
            GameArena gameArena =  game.getArena();
            if (gameArena.isEdit()){
                SPBedWars.getInstance().activeGames.remove(game);
                return;
            }
            for (GameSpawner gameSpawner: gameArena.getGameSpawners()){
                if (game.getGameStage().equals(GameStage.RUNNING)){
                    gameSpawner.itemTick();
                    if (gameSpawner.getBlockHologram() != null){
                        gameSpawner.getBlockHologram().rotate(2);
                    }
                }
            }
        }
        if (SPBedWars.getInstance().currentCreation != null && SPBedWars.getInstance().currentCreation.getArena().getGameSpawners() != null && SPBedWars.getInstance().currentCreation.getCreationStage().equals(CreationStage.SPAWNERS_ADD)){
            for (GameSpawner spawner: SPBedWars.getInstance().currentCreation.getArena().getGameSpawners()){
                spawner.itemTick();
                if (spawner.getBlockHologram() != null){
                    spawner.getBlockHologram().rotate(2);
                }
            }
        }

    }
}
