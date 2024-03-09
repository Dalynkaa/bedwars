package me.dalynkaa.spbedwars.infoServices.scoreboard;

import me.catcoder.sidebar.ProtocolSidebar;
import me.catcoder.sidebar.Sidebar;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardInit {
    Sidebar<Component> sidebar;
    BPlayer bPlayer;

    public ScoreboardInit(SPBedWars main, TeamPlayer player, GameStage stage) {
        this.bPlayer = player;
        sidebar = ProtocolSidebar.newAdventureSidebar(Component.text("Bed", TextColor.fromCSSHexString("#d63031")).append(Component.text("Ward", TextColor.fromCSSHexString("#ff7675"))), main);
        sidebar.removeViewers();
        if (stage.equals(GameStage.WAITING) || stage.equals(GameStage.WAITING_TIMER)) {
            waitingStage();
        } else if (stage.equals(GameStage.RUNNING)) {
            if (player.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
                specStage();
            } else {
                runningStage();
            }

        }

        sidebar.addBlankLine();
        sidebar.addLine(Component.text("play.spworlds.ru", TextColor.fromCSSHexString("#636e72")));
        sidebar.updateLinesPeriodically(0, 10, true);
        sidebar.addViewer(player.getPlayer());
    }

    public void clear() {
        sidebar.removeViewer(bPlayer.getPlayer());
    }

    public void waitingStage() {
        sidebar.addUpdatableLine(player -> {
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            BWGame game = SPBedWars.getInstance().activeGames.get(bPlayer.getCurrentGame());
            return Component.text(" Карта: ", TextColor.fromCSSHexString("#e17055")).append(Component.text(game.getArena().getArenaName(), TextColor.fromCSSHexString("#fab1a0")));
        });
        sidebar.addBlankLine();

        sidebar.addUpdatableLine(player -> {
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            BWGame game = SPBedWars.getInstance().activeGames.get(bPlayer.getCurrentGame());
            return Component.text(" Режим: ", TextColor.fromCSSHexString("#e17055")).append(Component.text(game.getArena().getArenaType().getTranslate(), TextColor.fromCSSHexString("#fab1a0")));
        });
    }

    public void runningStage() {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        BWGame game = bPlayer.getGame();
        sidebar.addLine(Component.text(dateInString, TextColor.fromCSSHexString("#636e72"))
                .append(Component.text(" / ")
                        .append(Component.text(game.getGameId().toString().substring(game.getGameId().toString().length() - 4)))));
        sidebar.addBlankLine();

        sidebar.addUpdatableLine(player -> {
            BPlayer bPlayer = BPlayer.getByUUID(player.getUniqueId());
            return (Component) Component.text("Алмазы ")
                    .append(Component.text("2"))
                    .append(Component.text(" через "))
                    .append(Component.text("0:00"));
        });
    }

    public void specStage() {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        BWGame game = bPlayer.getGame();
        sidebar.addLine(Component.text(dateInString, TextColor.fromCSSHexString("#636e72"))
                .append(Component.text(" / ")
                        .append(Component.text(game.getGameId().toString().substring(game.getGameId().toString().length() - 4)))));
        sidebar.addBlankLine();
    }

}
