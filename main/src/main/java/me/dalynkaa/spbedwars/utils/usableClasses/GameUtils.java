package me.dalynkaa.spbedwars.utils.usableClasses;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.concurrent.atomic.AtomicReference;

public class GameUtils {
    /**
     * Starts a timer for the specified game.
     *
     * @param game   the BWGame object representing the game
     * @param time   the duration of the timer in seconds
     * @param text   the text to display in the timer bar
     * @param action the action to execute when the timer ends
     */

    public static BossBar startTimer(BWGame game, Integer time, String text, ITimerEndAction<BWGame> action) {
        SPBedWars main = SPBedWars.getInstance();
        Bukkit.createBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()), text + " - ", BarColor.GREEN, BarStyle.SOLID).setVisible(true);

        AtomicReference<Integer> currentTime = new AtomicReference<>(0);
        Integer timerId = Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> {
            if (currentTime.get() >= time) {
                Bukkit.getScheduler().runTask(main, () -> {
                    action.execute(game);
                });
                Bukkit.getScheduler().cancelTask(main.timerMap.get(game.getGameId()));
                BossBar boss = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
                boss.setVisible(false);
                boss.removeAll();
                Bukkit.removeBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
                main.timerMap.remove(game.getGameId());
                return;
            }
            currentTime.updateAndGet(v -> v + 1);
            BossBar bossBar = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
            bossBar.setTitle(text + " - " + getDurationString(time - currentTime.get()));
            bossBar.setProgress(1 - getPercentage(time, currentTime.get()));
        }, 0, 20).getTaskId();
        main.timerMap.put(game.getGameId(), timerId);
        BossBar returnBar = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
        game.getPlayers().forEach(teamPlayer -> returnBar.addPlayer(teamPlayer.getPlayer()));
        return returnBar;
    }

    /**
     * Starts a player timer.
     *
     * @param player the TeamPlayer object representing the player
     * @param time   the duration of the timer in seconds
     * @param text   the text to display in the BossBar
     * @param action the action to execute when the timer ends
     * @return the BossBar object representing the timer
     */
    public static BossBar startPlayerTimer(TeamPlayer player, Integer time, String text, ITimerEndAction<BWGame> action) {
        SPBedWars main = SPBedWars.getInstance();
        BossBar bossBar = Bukkit.createBossBar(NamespacedKey.fromString("timer-" + player.getUuid().toString()), text + " - ", BarColor.GREEN, BarStyle.SOLID);
        bossBar.setVisible(true);

        AtomicReference<Integer> currentTime = new AtomicReference<>(0);
        Integer timerId = Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> {
            if (currentTime.get() >= time) {
                Bukkit.getScheduler().runTask(main, () -> {
                    action.execute(player.getGame());
                });
                Bukkit.getScheduler().cancelTask(main.timerMap.get(player.getUuid()));
                BossBar boss = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + player.getUuid().toString()));
                boss.setVisible(false);
                boss.removeAll();
                Bukkit.removeBossBar(NamespacedKey.fromString("timer-" + player.getUuid().toString()));
                main.timerMap.remove(player.getUuid());
                return;
            }
            currentTime.updateAndGet(v -> v + 1);
            bossBar.setTitle(text + " - " + getDurationString(time - currentTime.get()));
            bossBar.setProgress(1 - getPercentage(time, currentTime.get()));
        }, 0, 20).getTaskId();
        main.timerMap.put(player.getUuid(), timerId);
        bossBar.addPlayer(player.getPlayer());

        return bossBar;
    }

    /**
     * Cancels the timer for the specified BWGame.
     *
     * @param game the BWGame object for which the timer is to be canceled
     */
    public static void cancelTimer(BWGame game) {
        SPBedWars main = SPBedWars.getInstance();
        if (main.timerMap.get(game.getGameId()) != null) {
            Bukkit.getScheduler().cancelTask(main.timerMap.get(game.getGameId()));
            main.timerMap.remove(game.getGameId());
        }
        if (Bukkit.getBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString())) != null) {
            BossBar boss = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
            boss.setVisible(false);
            Bukkit.removeBossBar(NamespacedKey.fromString("timer-" + game.getGameId().toString()));
        }
    }

    public static double getPercentage(final double total, final double progress) {
        try {
            return (progress / (total / 100d)) / 100;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String getDurationString(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    public static String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
