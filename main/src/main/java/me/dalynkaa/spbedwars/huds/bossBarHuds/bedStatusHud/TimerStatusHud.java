package me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud;

import me.dalynkaa.spbedwars.huds.bossBarHuds.AbstractBossBarHud;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.Offset;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.StatusChars;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.StatusCharsNew;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.usableClasses.DeathMatchTimer;
import me.dalynkaa.spbedwars.utils.usableClasses.GameUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public class TimerStatusHud extends AbstractBossBarHud {
    private boolean showHud = false;
    private StatusChars icon;

    @Override
    public String getName() {
        return "TimerStatusHud";
    }

    @Override
    public RenderStatus render(UUID player) {
        BPlayer bPlayer = BPlayer.getByUUID(player);
        if (bPlayer == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text().asComponent());
        }
        BWGame game = bPlayer.getGame();
        if (game == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text().asComponent());
        }
        DeathMatchTimer timer = game.getDeathMatchTimer();
        if (timer == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text().asComponent());
        }
        if (!timer.isRunning()) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text().asComponent());
        }
        int mainHudSize = 0;
        Integer bgSize = 0;
        TextComponent.Builder bedHud = Component.text();
        Offset.pushHorizontalOffset(bedHud, 0);
        mainHudSize += 0;
        Integer minutesWidth = StatusCharsNew.parceNumber(bedHud, GameUtils.twoDigitString(timer.getRemainingTimeMinutes()));
        bgSize += minutesWidth;
        bedHud.append(Component.text(StatusCharsNew.CHAR_DOTS.getCharacter()).style((style) -> style.font(StatusCharsNew.CHAR_DOTS.getFont().getKey())));
        bgSize += StatusCharsNew.CHAR_DOTS.getWidht();
        Integer secondsWidth = StatusCharsNew.parceNumber(bedHud, GameUtils.twoDigitString(timer.getRemainingTimeSeconds()));
        bgSize += secondsWidth;
        int size = Offset.getBackground(bedHud, bgSize);
        mainHudSize += bgSize;
        mainHudSize += size;
        Offset.pushHorizontalOffset(bedHud, -mainHudSize);
        return new RenderStatus(RenderStatusType.RENDER, bedHud.build(), mainHudSize);
    }
}
