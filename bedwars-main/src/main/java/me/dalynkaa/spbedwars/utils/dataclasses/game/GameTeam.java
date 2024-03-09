package me.dalynkaa.spbedwars.utils.dataclasses.game;

import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.TeamUpgrades;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.teams.TeamBed;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.EntityEffect;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SerializableAs("team")
public class GameTeam implements ConfigurationSerializable {
    private Teams team;
    private GameLocation spawn;
    private TeamBed bedPos;
    private Boolean hasBed;
    private Boolean active;
    private List<TeamPlayer> teamPlayers;
    private List<TeamUpgrades> upgrades;

    public GameTeam(Teams team, GameLocation spawn, TeamBed bedPos) {
        this.team = team;
        this.spawn = spawn;
        this.bedPos = bedPos;
        this.hasBed = true;
        this.active = true;
        this.teamPlayers = new ArrayList<>();
        this.upgrades = new ArrayList<>();
    }

    public GameTeam(Teams team) {
        this.team = team;
        this.spawn = null;
        this.bedPos = null;
        this.hasBed = true;
        this.active = true;
        this.teamPlayers = new ArrayList<>();
        this.upgrades = new ArrayList<>();
    }

    public Teams getTeam() {
        return team;
    }

    public GameTeam setTeam(Teams team) {
        this.team = team;
        return this;
    }

    public List<TeamUpgrades> getUpgrades() {
        return upgrades;
    }

    public GameTeam addUpgrade(TeamUpgrades upgrade) {
        this.upgrades.add(upgrade);
        return this;
    }

    public GameTeam removeUpgrade(TeamUpgrades upgrade) {
        TeamUpgrades toRemove = null;
        for (TeamUpgrades tempUpgrade : getUpgrades()) {
            if (tempUpgrade.equals(upgrade)) {
                toRemove = tempUpgrade;
            }
        }
        if (toRemove != null) {
            this.upgrades.remove(toRemove);
        }
        return this;
    }

    public Boolean hasUpgrade(TeamUpgrades upgrade) {
        return getUpgrades().contains(upgrade);
    }

    public Boolean hasActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public GameLocation getSpawn() {
        return spawn;
    }

    public GameTeam setSpawn(GameLocation spawn) {
        this.spawn = spawn;
        return this;
    }


    public TeamBed getBedPos() {
        return bedPos;
    }

    public GameTeam setBedPos(TeamBed bedPos) {
        this.bedPos = bedPos;
        return this;
    }

    public List<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }

    public List<TeamPlayer> getActivePlayers() {
        return getTeamPlayers().stream().filter((player) -> !player.isLoose()).collect(Collectors.toList());
    }

    public GameTeam setTeamPlayers(List<TeamPlayer> teamPlayers) {
        this.teamPlayers = teamPlayers;
        return this;
    }

    public GameTeam addTeamPlayer(TeamPlayer teamPlayer) {
        this.teamPlayers.add(teamPlayer);
        return this;
    }

    public GameTeam removeTeamPlayer(TeamPlayer teamPlayer) {
        TeamPlayer toRemove = null;
        for (TeamPlayer tempPlayer : getTeamPlayers()) {
            if (tempPlayer.getUuid().equals(teamPlayer.getUuid())) {
                toRemove = tempPlayer;
            }
        }
        if (toRemove != null) {
            this.teamPlayers.remove(toRemove);
        }
        return this;
    }

    public void breakBed(BWGame game) {
        setHasBed(false);
        for (BPlayer bPlayer : game.getPlayers()) {
            bPlayer.sendMessage("Кровать команды " + getTeam().getName() + " сломана!", MessageType.SUCCESS);
        }
        for (TeamPlayer teamPlayer : getTeamPlayers()) {
            teamPlayer.sendMessage("Ваша кровать сломана", MessageType.ERROR);
            teamPlayer.getPlayer().playEffect(EntityEffect.TOTEM_RESURRECT);
        }
        getBedPos().destroy();
        getSpawn().getWorld().getEntitiesByClass(Item.class).forEach((item -> {
            if (TeamBed.isBed(item.getItemStack().getType())) {
                item.remove();
            }
        }));
    }

    public Boolean hasBed() {
        return hasBed;
    }

    public void setHasBed(Boolean hasBed) {
        this.hasBed = hasBed;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("team", getTeam().name());
        map.put("spawn", getSpawn());
        map.put("bedPos", getBedPos());
        return map;
    }

    public static GameTeam deserialize(Map<String, Object> map) {
        String teamS = (String) map.get("team");
        GameLocation spawnS = (GameLocation) map.get("spawn");
        TeamBed bedPosS = (TeamBed) map.get("bedPos");
        return new GameTeam(Teams.valueOf(teamS), spawnS, bedPosS);
    }

    @Override
    public String toString() {
        return "GameTeam{" +
                "team=" + team +
                ", spawn=" + spawn +
                ", bedPos=" + bedPos +
                ", teamPlayers=" + teamPlayers +
                '}';
    }
}
