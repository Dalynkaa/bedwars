package me.dalynkaa.spbedwars.utils.dataclasses.player;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.huds.MainHudRenderer;
import me.dalynkaa.spbedwars.infoServices.scoreboard.ScoreboardInit;
import me.dalynkaa.spbedwars.proxyUtils.BungeeMessanging;
import me.dalynkaa.spbedwars.utils.UUIDUtils;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.exeptions.GameFullExeption;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.UUID;

public class BPlayer {
    private UUID uuid;
    private UUID editArena;
    private UUID previusGame;
    private UUID currentGame;

    public BPlayer(UUID uuid, UUID editArena, UUID previusGame, UUID currentGame) {
        this.uuid = uuid;
        this.editArena = editArena;
        this.previusGame = previusGame;
        this.currentGame = currentGame;
    }

    public BPlayer(UUID uuid) {
        this.uuid = uuid;
        this.editArena = null;
        this.previusGame = null;
        this.currentGame = null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BPlayer setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getPreviusGame() {
        return previusGame;
    }

    public BPlayer setPreviusGame(UUID previusGame) {
        this.previusGame = previusGame;
        return this;
    }

    public UUID getEditArena() {
        return editArena;
    }

    public void setEditArena(UUID editArena) {
        this.editArena = editArena;
        if (editArena != null) {
            sendMessage(Component.text("Вы перешли в режим редактирования арены"), MessageType.NORMAL);
            return;
        }
        sendMessage(Component.text("Вы вышли из режима редактирования арены"), MessageType.NORMAL);
    }

    public UUID getCurrentGame() {
        return currentGame;
    }

    public BPlayer setCurrentGame(UUID currentGame) {
        this.currentGame = currentGame;
        return this;
    }

    public void sendMessage(Component message, MessageType type) {
        switch (type) {
            case NORMAL ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#636e72"))).append(message.color(TextColor.fromCSSHexString("#b2bec3"))));
            case SUCCESS ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#00b894"))).append(message.color(TextColor.fromCSSHexString("#55efc4"))));
            case WARNING ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#ffeaa7"))).append(message.color(TextColor.fromCSSHexString("#fdcb6e"))));
            case ERROR ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fab1a0"))).append(message.color(TextColor.fromCSSHexString("#e17055"))));
            case ANOTHER ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fa8b44"))).append(message));

        }
    }

    public void sendMessage(String message, MessageType type) {
        switch (type) {
            case NORMAL ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#636e72"))).append(Component.text(message, TextColor.fromCSSHexString("#b2bec3"))));
            case SUCCESS ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#00b894"))).append(Component.text(message, TextColor.fromCSSHexString("#55efc4"))));
            case WARNING ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#ffeaa7"))).append(Component.text(message, TextColor.fromCSSHexString("#fdcb6e"))));
            case ERROR ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fab1a0"))).append(Component.text(message, TextColor.fromCSSHexString("#e17055"))));
            case ANOTHER ->
                    getPlayer().sendMessage(SPBedWars.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fa8b44"))).append(Component.text(message)));
        }
    }

    public TeamPlayer joinGame(BWGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game must be not null");
        }
        if (game.getPlayers().size() >= game.getArena().getArenaType().getPlayers()) {
            throw new GameFullExeption("Game is full");
        }
        TeamPlayer teamPlayer = TeamPlayer.fromPlayer(this);
        MainHudRenderer.initializePlayer(this);
        game.addPlayer(teamPlayer);
        getPlayer().teleport(game.getArena().getLobbyLocation().getSpawn().getLocation());
        setCurrentGame(game.getGameId());
        save();
        new ScoreboardInit(SPBedWars.getInstance(), this.getTeamPlayer(), game.getGameStage());
        return teamPlayer;
    }

    public void leaveGame(BWGame game, Boolean force) {
        if (game != null) {
            TeamPlayer teamPlayer = TeamPlayer.fromPlayer(this);
            game.removePlayer(teamPlayer);
        }
        setCurrentGame(null);
        save();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public TeamPlayer getTeamPlayer() {
        return TeamPlayer.fromPlayer(this);
    }

    public static Boolean hasAccount(UUID uuid) {
        return SPBedWars.getInstance().getDatabase().getUserDatabase().hasUserInTable(uuid);
    }

    public static BPlayer getByUUID(UUID uuid) {
        if (hasAccount(uuid)) {
            ResultSet resultSet = SPBedWars.getInstance().getDatabase().getUserDatabase().getUserByUUID(uuid);
            try {
                String result_editArena = resultSet.getString("editArena");
                String result_prevGame = resultSet.getString("previusGameId");
                String result_currGame = resultSet.getString("GameId");
                return new BPlayer(uuid, UUIDUtils.getUUID(result_editArena, null), UUIDUtils.getUUID(result_prevGame, null), UUIDUtils.getUUID(result_currGame, null));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            BPlayer player = new BPlayer(uuid);
            player.save();
            return player;
        }
    }

    public void save() {
        if (hasAccount(getUuid())) {
            SPBedWars.getInstance().getDatabase().getUserDatabase().updateUser(this);
        } else {
            SPBedWars.getInstance().getDatabase().getUserDatabase().insertUser(getUuid());
        }
    }

    public void sendToLobby() {
        BungeeMessanging.sendServer(this, SPBedWars.getInstance().lobbyServerName);
    }

    public void sendToServer(String server) {
        BungeeMessanging.sendServer(this, server);
    }

    public BWGame getGame() {
        for (BWGame game : SPBedWars.getInstance().activeGames.values()) {
            for (BPlayer bPlayer : game.getPlayers()) {
                if (bPlayer.getUuid().equals(getUuid())) {
                    return game;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BPlayer{" +
                "uuid=" + uuid +
                ", editArena=" + editArena +
                ", previusGame=" + previusGame +
                ", currentGame=" + currentGame +
                '}';
    }
}
