package me.dalynkaa.spbedwars.utils.dataclasses.game;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.infoServices.scoreboard.ScoreboardInit;
import me.dalynkaa.spbedwars.utils.config.ArenaConfig;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import me.dalynkaa.spbedwars.utils.usableClasses.GameUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BWGame {
    private final UUID gameId;
    private GameArena arena;
    private GameStage gameStage;
    private List<TeamPlayer> players;
    private List<GameTeam> allTeams;
    private List<GameTeam> teamsInGame;
    private List<Block> placedBlocks;

    public BWGame(UUID gameId, GameArena arena, GameStage gameStage) {
        this.gameId = gameId;
        this.arena = arena;
        this.gameStage = gameStage;
        this.players = new ArrayList<>();
        this.allTeams = arena.getGameTeams().values().stream().toList();
        this.teamsInGame = arena.getGameTeams().values().stream().toList();
        this.placedBlocks = new ArrayList<>();
    }

    public UUID getGameId() {
        return gameId;
    }

    public GameArena getArena() {
        return arena;
    }

    public void setArena(GameArena arena) {
        this.arena = arena;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public List<TeamPlayer> getPlayers() {
        return players;
    }


    public List<GameTeam> getTeamsInGame() {
        return teamsInGame;
    }


    public List<GameTeam> getAllTeams() {
        return allTeams;
    }

    public BWGame addBlockToList(Block block) {
        this.placedBlocks.add(block);
        return this;
    }

    public BWGame removeBlockFromList(Block block) {
        this.placedBlocks.remove(block);
        return this;
    }

    public List<Block> getPlacedBlocks() {
        return this.placedBlocks;
    }

    public void addPlayer(TeamPlayer player, Teams team) {
        if (getPlayers().size() >= getArena().getArenaType().getPlayers()) {
            player.sendMessage("Игра заполнена", MessageType.ERROR);
            return;
        }
        this.players.add(player);
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.sendMessage(player.getPlayer().name().append(Component.text(" присоеденился")), MessageType.SUCCESS);
        }
    }

    public void addPlayer(TeamPlayer player) {
        if (getPlayers().size() >= getArena().getArenaType().getPlayers()) {
            player.sendMessage("Игра заполнена", MessageType.ERROR);
            return;
        }
        this.players.add(player);
        GameTeam gameTeam = chooseRandomTeamForPlayerToJoin(false, false);
        gameTeam.addTeamPlayer(player);
        this.teamsInGame = allTeams;
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.sendMessage(player.getPlayer().name().color(player.getGameTeam().getTeam().getTeamCollor()).append(Component.text(player.getGameTeam().getTeam().getPlayerIcon())).append(Component.text(" присоеденился", player.getGameTeam().getTeam().getSecondCollor())), MessageType.ANOTHER);
        }
        if (getPlayers().size() >= getArena().getArenaType().getPlayers()) {
            startPreGameTimer();
        }
        SPBedWars.getInstance().proxyUtils.updateGame(this);
    }

    public void removePlayer(TeamPlayer player) {
        if (getPlayers().size() >= getArena().getArenaType().getPlayers() && getGameStage().equals(GameStage.WAITING_TIMER)) {
            GameUtils.cancelTimer(this);
            setGameStage(GameStage.WAITING);
            SPBedWars.getInstance().proxyUtils.updateGame(this);
            for (TeamPlayer teamPlayer : getPlayers()) {
                teamPlayer.sendMessage("Таймер остановлен", MessageType.ERROR);
            }
        }
        TeamPlayer toRemove = null;
        for (TeamPlayer teamPlayer1 : getPlayers()) {
            if (teamPlayer1.getUuid().equals(player.getUuid())) {
                toRemove = teamPlayer1;
            }
        }
        GameTeam gameTeam = player.getGameTeam();
        for (GameTeam gameTeam1 : getTeamsInGame()) {
            if (gameTeam1.getTeam().equals(gameTeam.getTeam())) {
                gameTeam1.removeTeamPlayer(player);
            }
        }
        for (GameTeam gameTeam1 : getAllTeams()) {
            if (gameTeam1.getTeam().equals(gameTeam.getTeam())) {
                gameTeam1.removeTeamPlayer(player);
            }
        }
        if (toRemove != null) {
            this.players.remove(toRemove);
        }
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.sendMessage(player.getPlayer().name().append(Component.text(" вышел")), MessageType.SUCCESS);
        }
        SPBedWars.getInstance().proxyUtils.updateGame(this);
    }

    public @Nullable GameTeam chooseRandomTeamForPlayerToJoin(boolean ignoreTeamSize, boolean onlyActiveTeams) {
        GameTeam teamForJoin = null;
        if (!onlyActiveTeams && getArena().getGameTeams().values().size() < 2) {
            teamForJoin = getFirstTeamThatIsntInGame();
        } else {
            @Nullable GameTeam lowest = null;

            for (GameTeam team : getAllTeams()) {
                if (!ignoreTeamSize && team.getTeamPlayers().size() >= getArena().getArenaType().getPlayerForCommand()) {
                    continue; // skip full teams
                }

                if (lowest == null) {
                    lowest = team;
                }

                if (lowest.getTeamPlayers().size() > team.getTeamPlayers().size()) {
                    lowest = team;
                }
            }
            if (lowest != null) {
                teamForJoin = lowest;
            } else if (!onlyActiveTeams) {
                teamForJoin = getFirstTeamThatIsntInGame();
            }
        }

        return teamForJoin;
    }

    public GameTeam getFirstTeamThatIsntInGame() {
        for (GameTeam team : getAllTeams()) {
            return team;
        }
        return null;
    }

    public List<GameTeam> getActiveTeams() {
        List<GameTeam> activeTeams = new ArrayList<>();
        for (GameTeam gameTeam : getTeamsInGame()) {
            if (gameTeam.hasActive()) {
                activeTeams.add(gameTeam);
            }
        }
        return activeTeams;
    }

    public void startPreGameTimer() {
        setGameStage(GameStage.WAITING_TIMER);
        SPBedWars.getInstance().proxyUtils.updateGame(this);
        GameUtils.startTimer(this, 10, "До начала игры", (game) -> {
            startGameStage();
        });
        BossBar bossBar = Bukkit.getBossBar(NamespacedKey.fromString("timer-" + getGameId().toString()));
        for (TeamPlayer teamPlayer : getPlayers()) {
            bossBar.addPlayer(teamPlayer.getPlayer());
        }
    }

    public void startGameStage() {
        setGameStage(GameStage.RUNNING);
        SPBedWars.getInstance().proxyUtils.updateGame(this);
        for (BPlayer bPlayer : getPlayers()) {
            new ScoreboardInit(SPBedWars.getInstance(), bPlayer.getTeamPlayer(), getGameStage());
        }
        GameUtils.startIconTimer(this, 1200, (game) -> {
            for (GameTeam team : getTeamsInGame()) {
                if (!team.hasBed()) {
                    team.breakBed(this);
                }
            }
        });
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.spawn();
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(getArena().getWorld()))) {
            CuboidRegion cuboidRegion = new CuboidRegion(getArena().getLobby().getPos1().getBlockVector3(), getArena().getLobby().getPos2().getBlockVector3());
            editSession.setBlocks(cuboidRegion, BukkitAdapter.adapt(Material.AIR.createBlockData()));
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    public void endGame(GameTeam winner) {
        setGameStage(GameStage.GAME_END_CELEBRATING);
        SPBedWars.getInstance().proxyUtils.unregisterGame(this.getGameId());
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.sendMessage("Игра окончена", MessageType.SUCCESS);
            teamPlayer.getPlayer().teleport(getArena().getSpectatorLocation().getLocation());
            teamPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
            teamPlayer.getPlayer().getInventory().clear();
            new ScoreboardInit(SPBedWars.getInstance(), teamPlayer, getGameStage()).clear();
        }
        for (TeamPlayer teamPlayer : winner.getTeamPlayers()) {
            teamPlayer.sendMessage("Вы победили", MessageType.SUCCESS);
        }
        GameUtils.startTimer(this, 10, "До перемещения в лобби", (game) -> {
            for (TeamPlayer teamPlayer1 : getPlayers()) {
                teamPlayer1.sendToLobby();
            }
            createNewGame().clearArena();
        });


    }

    public void clearArena() {
        setGameStage(GameStage.REBUILDING);
        getArena().pasteSchem();
        List<Entity> entList = getArena().getWorld().getEntities();
        for (Entity current : entList) {
            if (current instanceof Item) {
                current.remove();
            }
            if (current instanceof ArmorStand) {
                current.remove();
            }
            if (current instanceof BlockDisplay) {
                current.remove();
            }
            if (current instanceof TextDisplay) {
                current.remove();
            }
        }
        for (GameSpawner gameSpawner : getArena().getGameSpawners()) {
            gameSpawner.despawn();
            gameSpawner.create();
        }
        for (GameShop gameShop : getArena().getGameShops()) {
            gameShop.despawn();
            gameShop.spawn();
        }
        for (GameTeam gameTeam : getArena().getGameTeams().values()) {
            gameTeam.getBedPos().setBedBlock();
        }
        setGameStage(GameStage.WAITING);
    }

    public BWGame createNewGame() {
        SPBedWars.getInstance().activeGames.remove(getGameId());
        UUID arenaId = UUID.randomUUID();
        BWGame game = new BWGame(arenaId, arena, GameStage.WAITING);
        SPBedWars.getInstance().activeGames.put(arenaId, game);
        World world = new WorldCreator(arena.getId().toString()).createWorld();
        SPBedWars.getInstance().proxyUtils.registerGame(game);
        return game;

    }

    public static BWGame getGameById(UUID gameid) {
        return SPBedWars.getInstance().activeGames.get(gameid);
    }

    public static void loadGames() {
        for (String s : ArenaConfig.getAllArenasConfig()) {
            Bukkit.getLogger().info(s + " -arena");
            GameArena arena1 = GameArena.getByID(UUID.fromString(s));
            if (Config.getServerEdit()) {
                arena1.setEdit(true);
                UUID arenaId = UUID.randomUUID();
                BWGame game = new BWGame(arenaId, arena1, GameStage.REBUILDING);
                SPBedWars.getInstance().editsGames.put(arenaId, game);
                World world = new WorldCreator(arena1.getId().toString()).createWorld();
                game.clearArena();
            }
            if (!arena1.isEdit()) {
                UUID arenaId = UUID.randomUUID();
                BWGame game = new BWGame(arenaId, arena1, GameStage.WAITING);
                SPBedWars.getInstance().activeGames.put(arenaId, game);
                World world = new WorldCreator(arena1.getId().toString()).createWorld();
                game.clearArena();
                SPBedWars.getInstance().proxyUtils.registerGame(game);
            }
        }
    }

    public static BWGame getGameByWorld(World world) {
        for (BWGame game : SPBedWars.getInstance().activeGames.values()) {
            if (game.getArena().getWorld().equals(world)) {
                return game;
            }
        }
        return null;
    }

    public static BWGame getFirstGameThatIsntRunning() {
        for (BWGame game : SPBedWars.getInstance().activeGames.values()) {
            if (game.getGameStage().equals(GameStage.WAITING)) {
                if (game.getPlayers().size() < game.getArena().getArenaType().getPlayers()) {
                    return game;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BWGame{" +
                "arena=" + arena +
                ", gameStage=" + gameStage +
                ", players=" + players +
                '}';
    }
}
