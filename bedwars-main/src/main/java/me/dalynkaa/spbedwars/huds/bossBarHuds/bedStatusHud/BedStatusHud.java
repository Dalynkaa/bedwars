package me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud;

import com.destroystokyo.paper.ClientOption;
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
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BedStatusHud extends AbstractBossBarHud {
    private int mainBgSize = 0;

    @Override
    public String getName() {
        return "BedStatusHud";
    }

    @Override
    public RenderStatus render(UUID player) {
        BPlayer bPlayer = BPlayer.getByUUID(player);
        mainBgSize = 0;
        if (bPlayer == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        BWGame game = bPlayer.getGame();
        if (game == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        if (game.getGameStage().equals(GameStage.WAITING) || game.getGameStage().equals(GameStage.WAITING_TIMER) || game.getGameStage().equals(GameStage.GAME_END_CELEBRATING)) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        TextComponent.Builder bedHud = Component.text();
        Offset.pushHorizontalOffset(bedHud, 3);
        mainBgSize += 3;

        for (GameTeam team : game.getAllTeams()) {
            drawBedStatus(bedHud, team);
            Offset.pushHorizontalOffset(bedHud, 4);
            mainBgSize += 4;
        }

        return new RenderStatus(RenderStatusType.RENDER, bedHud.build(), mainBgSize);
    }

    private void drawBedStatus(TextComponent.Builder string, GameTeam team) {
        Integer bgSize = 0;
        string.append(Component.text(team.getTeam().getBedIcon().getCharacter()).style((style) -> {
            style.font(team.getTeam().getBedIcon().getFont().getKey());
        }));
        bgSize += team.getTeam().getBedIcon().getWidht();
        if (team.hasBed()) {
            string.append(Component.text(Chars.YES_CHECK.getCharacter()).style((style) -> {
                style.font(Chars.BED_RED.getFont().getKey());
            }));
            bgSize += Chars.YES_CHECK.getWidht();
        } else {
            if (team.getActivePlayers().isEmpty()) {
                string.append(Component.text(Chars.NO_CROSS.getCharacter()).style((style) -> {
                    style.font(Chars.NO_CROSS.getFont().getKey());
                }));
                bgSize += Chars.NO_CROSS.getWidht();
            } else {
                bgSize += Chars.parceNumber(string, team.getActivePlayers().size());
                Offset.pushHorizontalOffset(string, 3);
                mainBgSize += 3;
            }
        }
        mainBgSize += bgSize;
        Offset.getBackground(string, bgSize);
    }
}
