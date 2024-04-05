package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.huds.MainHudRenderer;
import me.dalynkaa.spbedwars.proxyUtils.data.game.GameJoinRegistrator;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    public JoinListener(SPBedWars spBedWars) {
        spBedWars.getServer().getPluginManager().registerEvents(this, spBedWars);
    }

    @EventHandler
    public void joinListener(PlayerJoinEvent event) {
        event.joinMessage(Component.text(""));
        if (SPBedWars.getInstance().activeGames.values().isEmpty()) {
            return;
        }
        BPlayer bPlayer = BPlayer.getByUUID(event.getPlayer().getUniqueId());

        if (Config.getServerEdit()) {
            if (bPlayer.getPlayer().isOp()) {
                return;
            }
            bPlayer.getPlayer().kick(Component.text("Сервер находится в режиме редактирования\nПожалуйста перезайдите через 5с.\nИли свяжитесь с администрацией для решения проблемы"));
            return;
        }

        if (!SPBedWars.getInstance().gameJoinTemp.containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().kick();
            return;
        }
        GameJoinRegistrator registrator = SPBedWars.getInstance().gameJoinTemp.get(event.getPlayer().getUniqueId());
        if (registrator.getJoinType() == GameJoinRegistrator.JoinType.JOIN) {
            BWGame game = SPBedWars.getInstance().activeGames.get(registrator.getGameId());
            TeamPlayer teamPlayer = bPlayer.joinGame(game);
            teamPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
            SPBedWars.getInstance().gameJoinTemp.remove(event.getPlayer().getUniqueId());
            return;
        }
        if (registrator.getJoinType().equals(GameJoinRegistrator.JoinType.SPEC)) {
            bPlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
            BWGame game = SPBedWars.getInstance().activeGames.get(registrator.getGameId());
            bPlayer.getPlayer().teleport(game.getArena().getSpectatorLocation().getLocation());
            return;
        }
        bPlayer.getPlayer().kick();
//        BWGame game = BWGame.getFirstGameThatIsntRunning();
//        BPlayer bPlayer = BPlayer.getByUUID(event.getPlayer().getUniqueId());
//        if (bPlayer.getCurrentGame() != null){
//            bPlayer.setPreviusGame(bPlayer.getCurrentGame());
//            bPlayer.setCurrentGame(null);
//            bPlayer.save();
//        }
//        bPlayer.save();
//        TeamPlayer teamPlayer = bPlayer.joinGame(game);
//        teamPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void quitListener(PlayerQuitEvent event) {
        BPlayer bPlayer = BPlayer.getByUUID(event.getPlayer().getUniqueId());
        BWGame game = bPlayer.getGame();
        if (bPlayer.getCurrentGame() != null) {
            bPlayer.setCurrentGame(null);
            bPlayer.save();
            bPlayer.leaveGame(game, false);
            MainHudRenderer.removePlayer(bPlayer);
        }

    }
}
