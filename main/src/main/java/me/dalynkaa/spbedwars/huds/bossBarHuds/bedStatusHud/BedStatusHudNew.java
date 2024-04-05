package me.dalynkaa.spbedwars.huds.bossBarHuds.bedStatusHud;

import me.dalynkaa.spbedwars.huds.bossBarHuds.AbstractBossBarHud;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.Fonts;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.Offset;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.StatusCharsNew;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public class BedStatusHudNew extends AbstractBossBarHud {
    private int mainBgSize = 0;

    @Override
    public String getName() {
        return "BedStatusHudNew";
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
        Offset.pushHorizontalOffset(bedHud, -200);
        mainBgSize += -200;
        drawPlayerTeam(bedHud, bPlayer);
        Offset.pushHorizontalOffset(bedHud, 8);
        mainBgSize += 8;
        drawBedStatus(bedHud, game, bPlayer);
        Offset.pushHorizontalOffset(bedHud, -mainBgSize);
        return new RenderStatus(RenderStatusType.RENDER, bedHud.build(), mainBgSize);
    }

    private void drawBedStatus(TextComponent.Builder string, BWGame game, BPlayer bPlayer) {
        for (GameTeam team : game.getAllTeams()) {
            if (team.getTeam().equals(bPlayer.getTeamPlayer().getGameTeam().getTeam())) {
                continue;
            }
            drawTeamBedStatus(string, team);
            Offset.pushHorizontalOffset(string, 2);
            mainBgSize += 2;
        }
        Offset.pushHorizontalOffset(string, -2);
        Offset.getBackground(string, 0, true, false);
    }

    private void drawTeamBedStatus(TextComponent.Builder string, GameTeam team) {
        int bgSize = 0;
        boolean teamHasBed = team.hasBed();
        if (teamHasBed) {
            string.append(Component.text(StatusCharsNew.FLAG.getCharacter()).style((style) -> {
                style.color(team.getTeam().getTeamCollor());
                style.font(StatusCharsNew.FLAG.getFont().getKey());
            }));
            Offset.pushHorizontalOffset(string, 2);
            bgSize += StatusCharsNew.FLAG.getWidht() + 2;
            Offset.pushHorizontalOffset(string, -2);
            Offset.getBackground(string, bgSize, false, false);
            mainBgSize += bgSize - 2;
        } else {
            if (team.getActivePlayers().isEmpty()) {
                string.append(Component.text(StatusCharsNew.SKULL.getCharacter()).style((style) -> {
                    style.color(team.getTeam().getTeamCollor());
                    style.font(StatusCharsNew.SKULL.getFont().getKey());
                }));
                Offset.pushHorizontalOffset(string, 2);
                bgSize += StatusCharsNew.SKULL.getWidht() + 2;
                Offset.pushHorizontalOffset(string, -2);
                Offset.getBackground(string, bgSize, false, false);
                mainBgSize += bgSize - 2;
            } else {
                Offset.pushHorizontalOffset(string, 2);
                bgSize += 2;
                string.append(Component.text(StatusCharsNew.PLAYER.getCharacter()).style((style) -> {
                    style.color(team.getTeam().getTeamCollor());
                    style.font(StatusCharsNew.FLAG.getFont().getKey());
                }));
                TextComponent.Builder players = Component.text();
                Offset.pushHorizontalOffset(string, 2);
                bgSize += 2;
                int size = StatusCharsNew.parceNumber(players, 2);
                bgSize += size;
                string.append(players.style((style) -> {
                    style.color(team.getTeam().getSecondCollor());
                    style.font(Fonts.HUD.getKey());
                }));
                Offset.pushHorizontalOffset(string, 1);
                bgSize += StatusCharsNew.FLAG.getWidht() + 2;
                Offset.getBackground(string, bgSize, false, false);
                mainBgSize += bgSize + 1;
            }
        }

    }

    private void drawPlayerTeam(TextComponent.Builder string, BPlayer bPlayer) {
        int bgSize = 0;
        GameTeam team = bPlayer.getTeamPlayer().getGameTeam();
        string.append(Component.text(StatusCharsNew.FLAG.getCharacter()).style((style) -> {
            style.color(team.getTeam().getTeamCollor());
            style.font(StatusCharsNew.FLAG.getFont().getKey());
        }));
        Offset.pushHorizontalOffset(string, 2);
        bgSize += StatusCharsNew.FLAG.getWidht() + 2;
        for (TeamPlayer teamPlayer : team.getTeamPlayers()) {
            if (teamPlayer.isLoose()) {
                string.append(Component.text(StatusCharsNew.SKULL.getCharacter()).style((style) -> {
                    style.font(StatusCharsNew.SKULL.getFont().getKey());
                    style.color(team.getTeam().getSecondCollor());
                }));
                Offset.pushHorizontalOffset(string, 2);
                bgSize += StatusCharsNew.PLAYER.getWidht() + 2;
            } else {
                string.append(Component.text(StatusCharsNew.PLAYER.getCharacter()).style((style) -> {
                    style.font(StatusCharsNew.PLAYER.getFont().getKey());
                    style.color(team.getTeam().getSecondCollor());
                }));
                Offset.pushHorizontalOffset(string, 2);
                bgSize += StatusCharsNew.PLAYER.getWidht() + 2;
            }
        }
        Offset.pushHorizontalOffset(string, -4);
        Offset.getBackground(string, bgSize, false, true);
        mainBgSize += bgSize - 4;
    }
}
