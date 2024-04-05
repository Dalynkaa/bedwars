package me.dalynkaa.spbedwars.huds.actionBarHuds.gamePreStatusHud;

import me.dalynkaa.spbedwars.huds.actionBarHuds.AbstractActionBarHud;
import me.dalynkaa.spbedwars.huds.utils.RenderStatus;
import me.dalynkaa.spbedwars.huds.utils.RenderStatusType;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.Offset;
import me.dalynkaa.spbedwars.huds.utils.hudConstans.WaitingChars;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public class GameWaitingHud extends AbstractActionBarHud {

    @Override
    public String getName() {
        return "GameWaitingHud";
    }

    @Override
    public RenderStatus render(UUID playerUUID) {
        BPlayer bPlayer = BPlayer.getByUUID(playerUUID);
        if (bPlayer == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        BWGame game = bPlayer.getGame();
        if (game == null) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        if (!game.getGameStage().equals(GameStage.WAITING)) {
            return new RenderStatus(RenderStatusType.HIDE, Component.text(""));
        }
        Teams team = bPlayer.getTeamPlayer().getGameTeam().getTeam();
        //bedhud
        TextComponent.Builder bedHud = Component.text();
        Integer playerCount = game.getPlayers().size();
        int playerCountLength = String.valueOf(playerCount).length();
        Integer totalCount = game.getArena().getArenaType().getPlayers();
        int totalCountLength = String.valueOf(totalCount).length();
        Offset.pushHorizontalOffset(bedHud, -111);
        bedHud.append(Component.text(WaitingChars.BACKGROUND.getString(team))).style((style) -> style.font(
                WaitingChars.BACKGROUND.getFont().getKey()));

        Offset.pushHorizontalOffset(bedHud, -(WaitingChars.BACKGROUND.getWidth()));
        Offset.pushHorizontalOffset(bedHud, 17);
        bedHud.append(Component.text(WaitingChars.GAME_WAITING.getColoredCharacter(team))).style((style) -> style.font(WaitingChars.GAME_WAITING.getFont().getKey()));

        Offset.pushHorizontalOffset(bedHud, -(WaitingChars.GAME_WAITING.getWidth()) - 17);
        //offset +
        int NUMBER_WIDTH = 16;
        if (playerCountLength == 1 && totalCountLength == 1) {
            Offset.pushHorizontalOffset(bedHud, 50);
        } else if (playerCountLength == 1 && totalCountLength == 2) {
            Offset.pushHorizontalOffset(bedHud, (61 - ((long) (totalCountLength - 1) * NUMBER_WIDTH)));
        } else if (playerCountLength == 2 && totalCountLength == 2) {
            Offset.pushHorizontalOffset(bedHud, (50 - ((long) (playerCountLength - 1) * NUMBER_WIDTH)));
        }
        // end offset +
        WaitingChars.parseNumber(bedHud, playerCount, team);
        bedHud.append(Component.text(WaitingChars.SLASH.getColoredCharacter(team))).style((style) -> style.font(WaitingChars.SLASH.getFont().getKey()));
        WaitingChars.parseNumber(bedHud, totalCount, team);
        //offset -
        if (playerCountLength == 1 && totalCountLength == 1) {
            Offset.pushHorizontalOffset(bedHud, -50);
        } else if (playerCountLength == 1 && totalCountLength == 2) {
            Offset.pushHorizontalOffset(bedHud, (-45 - ((long) (totalCountLength - 1) * NUMBER_WIDTH)));
        } else if (playerCountLength == 2 && totalCountLength == 2) {
            Offset.pushHorizontalOffset(bedHud, (-50 - ((long) (playerCountLength - 1) * NUMBER_WIDTH)));
        }
        // end offset -
        //bedhud end
        return new RenderStatus(RenderStatusType.RENDER, bedHud.build());
    }
}
