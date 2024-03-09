package me.dalynkaa.spbedwars.utils;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameTeam;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApis extends PlaceholderExpansion {
    private final SPBedWars plugin;
    public PlaceholderApis(SPBedWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "bedwars";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dalynkaa";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("team_collor")){
            TeamPlayer teamPlayer = TeamPlayer.fromPlayer(BPlayer.getByUUID(player.getUniqueId()));
            if (teamPlayer == null){
                return "";
            }
            GameTeam team = teamPlayer.getGameTeam();
            if (team == null){
                return "&#636e72";
            }
            TextColor color = team.getTeam().getTeamCollor();
            return "&"+color.asHexString();
        }

        return null;
    }
}
