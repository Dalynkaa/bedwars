package me.dalynkaa.spbedwars.utils.dataclasses.game;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import me.dalynkaa.bedwars.api.BedWarsMusicApi;
import me.dalynkaa.bedwars.api.interfaces.MusicPlayerSource;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.infoServices.scoreboard.ScoreboardInit;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.config.ArenaConfig;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameStage;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import me.dalynkaa.spbedwars.utils.usableClasses.DeathMatchTimer;
import me.dalynkaa.spbedwars.utils.usableClasses.GameUtils;
import net.kyori.adventure.text.Component;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
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
    private World world;
    private DeathMatchTimer deathMatchTimer;
    private MusicPlayerSource musicPlayerSource;
    private int deleteWorldTask = 0;

    public BWGame(UUID gameId, GameArena arena, GameStage gameStage) {
        this.gameId = gameId;
        this.arena = arena;
        this.gameStage = gameStage;
        this.players = new ArrayList<>();
        this.allTeams = arena.getGameTeams().values().stream().toList();
        this.teamsInGame = arena.getGameTeams().values().stream().toList();
        this.placedBlocks = new ArrayList<>();
        this.deathMatchTimer = null;
        this.musicPlayerSource = null;
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

    public World getWorld() {
        return this.world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public DeathMatchTimer getDeathMatchTimer() {
        return deathMatchTimer;
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
        if (deleteWorldTask != 0) {
            stopDeleteWorldTask();
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

    public void startDeleteWorldTask() {
        Logger.info("Start delete world task for game - " + getGameId().toString());
        deleteWorldTask = Bukkit.getScheduler().scheduleSyncDelayedTask(SPBedWars.getInstance(), this::removeCurrentGame, 20L * 20L);
    }

    public void stopDeleteWorldTask() {
        Logger.info("Stop delete world task for game - " + getGameId().toString());
        Bukkit.getScheduler().cancelTask(deleteWorldTask);
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
        if (players.isEmpty()) {
            startDeleteWorldTask();
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
        if (musicPlayerSource != null) {
            musicPlayerSource.play();
        }
        SPBedWars.getInstance().proxyUtils.updateGame(this);
        for (BPlayer bPlayer : getPlayers()) {
            new ScoreboardInit(SPBedWars.getInstance(), bPlayer.getTeamPlayer(), getGameStage());
        }
        this.deathMatchTimer = DeathMatchTimer.startTimer(1200, (game) -> {
            for (GameTeam team : getTeamsInGame()) {
                if (team.hasBed()) {
                    team.breakBed(this);
                }
            }
        }, this);
        for (TeamPlayer teamPlayer : getPlayers()) {
            teamPlayer.spawn();
        }
        Logger.debug(getWorld().getName() + " world name");
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(getWorld()))) {
            CuboidRegion cuboidRegion = new CuboidRegion(getArena().getLobby().getPos1().getBlockVector3(), getArena().getLobby().getPos2().getBlockVector3());
            editSession.setBlocks((Region) cuboidRegion, BukkitAdapter.adapt(Material.AIR.createBlockData()));
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

    }

    public void endGame(GameTeam winner) {
        setGameStage(GameStage.GAME_END_CELEBRATING);
        if (musicPlayerSource != null) {
            musicPlayerSource.stop();
        }
        if (getDeathMatchTimer() != null) {
            getDeathMatchTimer().stop();
        }
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
            removeCurrentGame();
        });
    }

    public void clearArena() {
        clearArena(getWorld());
    }

    public void clearArena(World world) {
        setGameStage(GameStage.REBUILDING);
        getArena().pasteSchem(world);
        List<Entity> entList = world.getEntities();
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


    public void removeCurrentGame() {
        World world = getWorld();
        if (world == null) {
            Logger.error("World is null");
            return;
        }
        Bukkit.unloadWorld(world, false);
        SPBedWars.getInstance().activeGames.remove(getGameId());
        SPBedWars.getInstance().proxyUtils.unregisterGame(getGameId());
        SPBedWars.getInstance().editsGames.remove(getGameId());
        File worldFile = new File(getGameId().toString());
        try {
            if (worldFile.exists()) {
                FileUtils.deleteDirectory(worldFile);
                Logger.debug("World " + getGameId() + " deleted");
            }
        } catch (IOException e) {
            Logger.error("Error while deleting world " + getGameId());
            e.printStackTrace();
        }
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
                UUID gameId = UUID.randomUUID();
                BWGame game = new BWGame(gameId, arena1, GameStage.REBUILDING);
                SPBedWars.getInstance().editsGames.put(gameId, game);
                new WorldCreator(gameId.toString()).createWorld();
                game.clearArena();
            }
            if (!arena1.isEdit()) {
                UUID gameId = UUID.randomUUID();
                BWGame game = new BWGame(gameId, arena1, GameStage.WAITING);
                SPBedWars.getInstance().activeGames.put(gameId, game);
                new WorldCreator(gameId.toString()).createWorld();
                game.clearArena();
                SPBedWars.getInstance().proxyUtils.registerGame(game, false);
            }
        }
    }

    public static BWGame createNewGame(UUID arenaId, ArenaTypes arenaType) {
        GameArena arena = GameArena.getByID(arenaId);
        arena.setArenaType(arenaType);
        UUID gameId = UUID.randomUUID();
        arena.copyWorld(gameId);
        BWGame game = new BWGame(gameId, arena, GameStage.WAITING);
        SPBedWars.getInstance().activeGames.put(gameId, game);
        World world = new WorldCreator(gameId.toString()).createWorld();
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setTime(5000L);
        world.setClearWeatherDuration(1000000);
        if (world == null) {
            Logger.error("World is null");
            game.removeCurrentGame();
            return null;
        }
        game.getArena().setWorld(world);
        game.clearArena(world);
        game.setWorld(world);
        UUID musicPlayerSource = BedWarsMusicApi.getApi().createMusicPlayerSource("game-" + gameId + "-music", arena.getId());
        game.musicPlayerSource = BedWarsMusicApi.getApi().getMusicPlayerSource(musicPlayerSource);
        SPBedWars.getInstance().proxyUtils.registerGame(game, false);
        return game;
    }

    public static BWGame getGameByWorld(World world) {
        for (BWGame game : SPBedWars.getInstance().activeGames.values()) {
            if (game.getWorld().equals(world)) {
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
