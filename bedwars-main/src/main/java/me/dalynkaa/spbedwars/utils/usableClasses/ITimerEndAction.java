package me.dalynkaa.spbedwars.utils.usableClasses;

import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;

public interface ITimerEndAction<T extends BWGame> {
    void execute(final T player);
}
