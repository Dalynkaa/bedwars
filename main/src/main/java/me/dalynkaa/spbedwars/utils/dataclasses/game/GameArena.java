package me.dalynkaa.spbedwars.utils.dataclasses.game;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.dalynkaa.bedwars.api.BedWarsMusicApi;
import me.dalynkaa.bedwars.api.interfaces.MusicPlayList;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.config.ArenaConfig;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ArenaTypes;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@SerializableAs("arena")
public class GameArena implements ConfigurationSerializable {
    private UUID id;
    private String arenaName;
    private String schemName;
    private World world;
    private boolean edit;
    private ArenaTypes arenaType;
    private GameLocation pos1;
    private GameLocation pos2;
    private GameLobby lobby;
    private GameLocation spectatorLocation;
    private List<GameShop> gameShops;
    private List<GameSpawner> gameSpawners;
    private Map<String, GameTeam> gameTeams;


    public GameArena(UUID id, String arenaName, String schemName, World world, boolean edit, ArenaTypes arenaType, GameLocation pos1, GameLocation pos2, GameLobby lobby, GameLocation spectatorLocation, List<GameShop> gameShops, List<GameSpawner> gameSpawners, Map<String, GameTeam> gameTeams) {
        this.id = id;
        this.arenaName = arenaName;
        this.schemName = schemName;
        this.world = world;
        this.edit = edit;
        this.arenaType = arenaType;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.lobby = lobby;
        this.spectatorLocation = spectatorLocation;
        this.gameShops = gameShops;
        this.gameSpawners = gameSpawners;
        this.gameTeams = gameTeams;
    }

    public GameArena(UUID id, String arenaName, String schemName, World world, ArenaTypes arenaType) {
        this.id = id;
        this.arenaName = arenaName;
        this.schemName = schemName;
        this.world = world;
        this.edit = true;
        this.arenaType = arenaType;
        this.pos1 = null;
        this.pos2 = null;
        this.lobby = null;
        this.spectatorLocation = null;
        this.gameShops = new ArrayList<>();
        this.gameSpawners = new ArrayList<>();
        this.gameTeams = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public GameArena setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getArenaName() {
        return arenaName;
    }

    public GameArena setArenaName(String arenaName) {
        this.arenaName = arenaName;
        return this;
    }

    public void setSchemName(String schemName) {
        this.schemName = schemName;
    }

    public String getSchemName() {
        return schemName;
    }

    public ArenaTypes getArenaType() {
        return arenaType;
    }

    public GameArena setArenaType(ArenaTypes arenaType) {
        this.arenaType = arenaType;
        return this;
    }

    public GameLocation getPos1() {
        return pos1;
    }

    public GameArena setPos1(GameLocation pos1) {
        this.pos1 = pos1;
        return this;
    }

    public GameLocation getPos2() {
        return pos2;
    }

    public GameArena setPos2(GameLocation pos2) {
        this.pos2 = pos2;
        return this;
    }

    public GameLobby getLobbyLocation() {
        return lobby;
    }

    public GameArena setLobbyLocation(GameLobby lobby) {
        this.lobby = lobby;
        return this;
    }

    public GameLocation getSpectatorLocation() {
        return spectatorLocation;
    }

    public GameArena setSpectatorLocation(GameLocation spectatorLocation) {
        this.spectatorLocation = spectatorLocation;
        return this;
    }

    public World getWorld() {
        return Bukkit.getWorld(getId().toString());
    }

    public GameArena setWorld(World world) {
        this.world = world;
        spectatorLocation.setWorld(world.getName());
        lobby.getPos1().setWorld(world.getName());
        lobby.getPos2().setWorld(world.getName());
        lobby.getSpawn().setWorld(world.getName());
        lobby.getPos1().setWorld(world.getName());
        lobby.getPos2().setWorld(world.getName());
        for (GameShop gameShop : gameShops) {
            gameShop.getShopPosition().setWorld(world.getName());
        }
        for (GameSpawner gameSpawner : gameSpawners) {
            gameSpawner.getLocation().setWorld(world.getName());
        }
        for (GameTeam gameTeam : gameTeams.values()) {
            gameTeam.getSpawn().setWorld(world.getName());
        }
        for (GameTeam gameTeam : gameTeams.values()) {
            gameTeam.getBedPos().getBedPos1().setWorld(world.getName());
        }
        return this;
    }

    public GameLobby getLobby() {
        return lobby;
    }

    public GameArena setLobby(GameLobby lobby) {
        this.lobby = lobby;
        return this;
    }

    public List<GameShop> getGameShops() {
        return gameShops;
    }

    public GameArena setGameShops(List<GameShop> gameShops) {
        this.gameShops = gameShops;
        return this;
    }

    public List<GameSpawner> getGameSpawners() {
        return gameSpawners;
    }

    public GameArena setGameSpawners(List<GameSpawner> gameSpawners) {
        this.gameSpawners = gameSpawners;
        return this;
    }

    public Map<String, GameTeam> getGameTeams() {
        return gameTeams;
    }

    public GameArena setGameTeams(Map<String, GameTeam> gameTeams) {
        this.gameTeams = gameTeams;
        return this;
    }

    public boolean isEdit() {
        return edit;
    }

    public GameArena setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    public void save() {
        ArenaConfig arenaConfig = ArenaConfig.getArenaConfig(getId().toString());
        arenaConfig.getCustomConfig().set("arena", this);
        try {
            arenaConfig.saveCustomConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameArena getByID(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        if (ArenaConfig.hasArenaConfig(uuid.toString())) {
            ArenaConfig arenaConfig = ArenaConfig.getArenaConfig(uuid.toString());
            GameArena gameArena = (GameArena) arenaConfig.getCustomConfig().get("arena", GameArena.class);
            return gameArena;
        }
        return null;
    }

    public void pasteSchem(World world) {
        File myfile = new File(SPBedWars.getInstance().getDataFolder().getAbsolutePath() + "/schem/" + getSchemName());
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(myfile);
        try (ClipboardReader reader = format.getReader(new FileInputStream(myfile))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(0, 0, 0))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadArenaPlayList() {
        MusicPlayList musicPlayList = BedWarsMusicApi.getApi().createMusicPlayList(getId());
        if (!musicPlayList.getQueue().isEmpty()) {
            return;
        }
        ArenaConfig arenaConfig = ArenaConfig.getArenaConfig(getId().toString());
        List<String> musicList = arenaConfig.getAllMusic();
        for (String music : musicList) {
            musicPlayList.loadTrack(music);
        }
        Bukkit.getScheduler().runTaskLater(SPBedWars.getInstance(), musicPlayList::save, 20L * 5);

    }

    public void copyWorld(UUID gameId) {
        File worldFolder = new File(SPBedWars.getInstance().getDataFolder().getAbsolutePath() + File.separator + "arenas" + File.separator + getId().toString() + File.separator + "{world}");
        File newWorld = new File(gameId.toString());
        try {
            FileUtils.copyDirectory(worldFolder, newWorld);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File uid = new File(newWorld, "uid.dat");
        if (uid.exists()) {
            uid.delete();
        }
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId().toString());
        map.put("arenaName", getArenaName());
        map.put("arenaSchem", getSchemName());
        map.put("world", world.getName());
        map.put("edit", isEdit());
        map.put("arenaType", getArenaType().name());
        map.put("pos1", getPos1());
        map.put("pos2", getPos2());
        map.put("lobby", getLobby());
        map.put("spectatorLocation", getSpectatorLocation());
        map.put("gameShops", getGameShops());
        map.put("gameSpawners", getGameSpawners());
        map.put("gameTeams", getGameTeams());
        return map;
    }

    public static GameArena deserialize(Map<String, Object> map) {
        UUID gameid1 = UUID.fromString((String) map.get("id"));
        String arenaName1 = (String) map.get("arenaName");
        String arenaSchem1 = (String) map.get("arenaSchem");
        World world1 = Bukkit.getWorld((String) map.get("world"));
        boolean edit = (boolean) map.get("edit");
        ArenaTypes arenaType1 = ArenaTypes.valueOf((String) map.get("arenaType"));
        GameLocation pos11 = (GameLocation) map.get("pos1");
        GameLocation pos21 = (GameLocation) map.get("pos2");
        GameLobby gameLobby1 = (GameLobby) map.get("lobby");
        GameLocation spectatorLocation1 = (GameLocation) map.get("spectatorLocation");
        List<GameShop> gameShopList = (List<GameShop>) map.get("gameShops");
        List<GameSpawner> gameSpawnerList = (List<GameSpawner>) map.get("gameSpawners");
        Map<String, GameTeam> gameTeamList = (Map<String, GameTeam>) map.get("gameTeams");
        return new GameArena(gameid1, arenaName1, arenaSchem1, world1, edit, arenaType1, pos11, pos21, gameLobby1, spectatorLocation1, gameShopList, gameSpawnerList, gameTeamList);
    }

    @Override
    public String toString() {
        return "GameArena{" +
                "id=" + id +
                ", arenaName='" + arenaName + '\'' +
                ", world=" + world +
                ", edit=" + edit +
                ", arenaType=" + arenaType +
                ", pos1=" + pos1 +
                ", pos2=" + pos2 +
                ", lobby=" + lobby +
                ", spectatorLocation=" + spectatorLocation +
                ", gameShops=" + gameShops +
                ", gameSpawners=" + gameSpawners +
                ", gameTeams=" + gameTeams +
                '}';
    }
}
