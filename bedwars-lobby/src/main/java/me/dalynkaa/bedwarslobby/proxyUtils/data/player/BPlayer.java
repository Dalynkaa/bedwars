package me.dalynkaa.bedwarslobby.proxyUtils.data.player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.gameMenus.gameselect.GameSelectMenu;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ArenaTypes;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.GameStage;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.MessageType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.ServerType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameJoinRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.PlayerServerMoveRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import me.dalynkaa.bedwarslobby.utils.UUIDUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.util.UUID;

import static me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator.findSuitableGame;

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
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#636e72"))).append(message.color(TextColor.fromCSSHexString("#b2bec3"))));
            case SUCCESS ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#00b894"))).append(message.color(TextColor.fromCSSHexString("#55efc4"))));
            case WARNING ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#ffeaa7"))).append(message.color(TextColor.fromCSSHexString("#fdcb6e"))));
            case ERROR ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fab1a0"))).append(message.color(TextColor.fromCSSHexString("#e17055"))));
            case ANOTHER ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fa8b44"))).append(message));

        }
    }

    public void sendMessage(String message, MessageType type) {
        switch (type) {
            case NORMAL ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#636e72"))).append(Component.text(message, TextColor.fromCSSHexString("#b2bec3"))));
            case SUCCESS ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#00b894"))).append(Component.text(message, TextColor.fromCSSHexString("#55efc4"))));
            case WARNING ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#ffeaa7"))).append(Component.text(message, TextColor.fromCSSHexString("#fdcb6e"))));
            case ERROR ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fab1a0"))).append(Component.text(message, TextColor.fromCSSHexString("#e17055"))));
            case ANOTHER ->
                    getPlayer().sendMessage(SPBedWarsLobby.getInstance().PREFIX.append(Component.text(" : ", TextColor.fromCSSHexString("#fa8b44"))).append(Component.text(message)));
        }
    }

    public TeamPlayer joinGame(GameRegistrator game) {
        if (game == null) {
            throw new IllegalArgumentException("Game must be not null");
        }
        if (game.getPlayers().size() >= game.getGameType().getPlayers()) {
            throw new IllegalArgumentException("Game is full");
        }
        TeamPlayer teamPlayer = TeamPlayer.fromPlayer(this);
        setCurrentGame(game.getGameId());
        save();
        SPBedWarsLobby.getInstance().getProxyUtils().requestGameJoin(game, this, GameJoinRegistrator.JoinType.JOIN);
        //sendServer(SPBedWarsLobby.getInstance().servers.get(game.getServerID()).getServerName());
        return teamPlayer;
    }

    public TeamPlayer specGame(GameRegistrator game) {
        if (game == null) {
            throw new IllegalArgumentException("Game must be not null");
        }
        TeamPlayer teamPlayer = TeamPlayer.fromPlayer(this);
        save();
        SPBedWarsLobby.getInstance().getProxyUtils().requestGameJoin(game, this, GameJoinRegistrator.JoinType.SPEC);
        sendServer(SPBedWarsLobby.getInstance().servers.get(game.getServerID()).getServerName());
        return teamPlayer;
    }

    public void sendServer(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("move");
        out.writeUTF(new PlayerServerMoveRegistrator(getUuid(), server).toJson());
        getPlayer().sendPluginMessage(SPBedWarsLobby.getInstance(), "bedwars:move", out.toByteArray());
    }
//    public void leaveGame(BWGame game, Boolean force){
//        if (game != null){
//            TeamPlayer teamPlayer = TeamPlayer.fromPlayer(this);
//            game.removePlayer(teamPlayer);
//        }
//        setCurrentGame(null);
//        save();
//    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public OfflinePlayer getOPlayer() {
        return Bukkit.getOfflinePlayer(getUuid());
    }

    public TeamPlayer getTeamPlayer() {
        return TeamPlayer.fromPlayer(this);
    }

    public static Boolean hasAccount(UUID uuid) {
        return SPBedWarsLobby.getInstance().getDatabase().getUserDatabase().hasUserInTable(uuid);
    }

    public void openMenu() {
        new GameSelectMenu(this);
    }

    public void connectToGame(ArenaTypes type, ServerType serverType) {
        if (type == null) {
            throw new IllegalArgumentException("Type must be not null");
        }
        if (getEditArena() != null) {
            sendMessage("Вы не можете присоединиться к игре, пока находитесь в режиме редактирования арены", MessageType.ERROR);
            return;
        }
        if (getCurrentGame() != null) {
            sendMessage("Вы не можете присоединиться к игре, пока находитесь в игре", MessageType.ERROR);
            return;
        }
        GameRegistrator game = findSuitableGame(serverType, type);

        if (game == null) {
            sendMessage("Нет доступных серверов", MessageType.ERROR);
            return;
        }
        joinGame(game);
    }

    @Nullable
    private static GameRegistrator getFirstGame(ServerType type, ArenaTypes arenaType) {
        GameRegistrator game = null;
        for (ServerRegistrator server : SPBedWarsLobby.getInstance().servers.values()) {
            if (server.getServerType().equals(type)) {
                for (GameRegistrator gameRegistrator : server.getGames()) {
                    if (gameRegistrator.getGameType().equals(arenaType)) {
                        if (gameRegistrator.getGameStage().equals(GameStage.WAITING)) {
                            if (gameRegistrator.getPlayers().size() <= game.getGameType().getPlayers()) {
                                game = gameRegistrator;
                                break;
                            }
                        }
                    }

                }
            }
        }
        return game;
    }

    public static BPlayer getByUUID(UUID uuid) {
        if (hasAccount(uuid)) {
            ResultSet resultSet = SPBedWarsLobby.getInstance().getDatabase().getUserDatabase().getUserByUUID(uuid);
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
            SPBedWarsLobby.getInstance().getDatabase().getUserDatabase().updateUser(this);
        } else {
            SPBedWarsLobby.getInstance().getDatabase().getUserDatabase().insertUser(getUuid());
        }
    }
}
