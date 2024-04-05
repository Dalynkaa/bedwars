package me.dalynkaa.spbedwars.utils.usableClasses;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import org.bukkit.Bukkit;

public class DeathMatchTimer {
    private int time = 0;
    private boolean isRunning = false;
    private int maxTime = 0;
    private final ITimerEndAction<BWGame> action;
    private final BWGame game;
    private int timerId = 0;


    public DeathMatchTimer(int maxTime, ITimerEndAction<BWGame> action, BWGame game) {
        this.maxTime = maxTime;
        this.action = action;
        this.game = game;
    }

    public static DeathMatchTimer startTimer(int maxTime, ITimerEndAction<BWGame> action, BWGame game) {
        DeathMatchTimer timer = new DeathMatchTimer(maxTime, action, game);
        timer.start();
        return timer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Integer getRemainingTimeSeconds() {
        return Integer.parseInt(GameUtils.getDurationString(maxTime - time).split(":")[1].replaceAll(" ", ""));
    }

    public Integer getRemainingTimeMinutes() {
        return Integer.parseInt(GameUtils.getDurationString(maxTime - time).split(":")[0].replaceAll(" ", ""));
    }

    private void start() {
        isRunning = true;
        SPBedWars main = SPBedWars.getInstance();
        this.timerId = Bukkit.getScheduler().runTaskTimerAsynchronously(main, () -> {
            if (time >= maxTime) {
                Bukkit.getScheduler().runTask(main, () -> {
                    action.execute(game);
                });
                Bukkit.getScheduler().cancelTask(this.timerId);
                isRunning = false;
                return;
            }
            this.time++;
        }, 0, 20).getTaskId();
    }

    public void stop() {
        isRunning = false;
        Bukkit.getScheduler().cancelTask(this.timerId);
    }
}
