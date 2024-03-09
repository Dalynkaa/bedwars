package me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud;

import me.dalynkaa.spbedwars.huds.bossBarHuds.AbstractBossBarHud;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.hudConstans.Chars;
import me.dalynkaa.spbedwars.utils.hudConstans.Offset;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public class TimerStatusHud extends AbstractBossBarHud {

    @Override
    public String getName() {
        return "TimerStatusHud";
    }

    @Override
    public RenderStatus render(UUID player) {
        int mainHudSize = 0;
        TextComponent.Builder bedHud = Component.text();
        Integer bgSize = 0;
        bedHud.append(Component.text(Chars.BED_BLUE.getCharacter()).style((style) -> {
            style.font(Chars.BED_BLUE.getFont().getKey());
        }));
        bgSize += Chars.BED_BLUE.getWidht();
        mainHudSize += bgSize;

        return new RenderStatus(RenderStatusType.RENDER, bedHud.build(), mainHudSize);
    }
}
