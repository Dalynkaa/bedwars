package me.dalynkaa.spbedwars.utils.dataclasses.another;


import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.PlayerUtils;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ItemSpawner;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.MessageType;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.Teams;
import me.dalynkaa.spbedwars.utils.dataclasses.game.*;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.CreationStage;
import me.dalynkaa.spbedwars.utils.dataclasses.game.enums.GameShopType;
import me.dalynkaa.spbedwars.utils.dataclasses.game.teams.TeamBed;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;
import me.dalynkaa.spbedwars.utils.usableClasses.InventoryButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArenaCreation {
    private GameArena arena;
    private BPlayer player;
    private CreationStage creationStage;

    public ArenaCreation(GameArena arena, BPlayer player) {
        this.arena = arena;
        this.player = player;
        this.creationStage = CreationStage.NOTHING;
    }

    public GameArena getArena() {
        return arena;
    }

    public ArenaCreation setArena(GameArena arena) {
        this.arena = arena;
        return this;
    }

    public BPlayer getPlayer() {
        return player;
    }

    public ArenaCreation setPlayer(BPlayer player) {
        this.player = player;
        return this;
    }

    public CreationStage getCreationStage() {
        return creationStage;
    }

    public ArenaCreation setCreationStage(CreationStage creationStage) {
        this.creationStage = creationStage;
        return this;
    }

    public void cornersSetStage() {
        setCreationStage(CreationStage.ARENA_REGION);
        getPlayer().getPlayer().getInventory().clear();
        ItemStack setPos1 = InventoryButton.from(Material.PAPER)
                .setName(Component.text("Установить первый угол арены"))
                .setLore(Component.text("Нажмите ПКМ и стойте там где будет первый угол"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location playerLocation = eventPlayer.getLocation();
                    getArena().setPos1(GameLocation.fromLocation(playerLocation));
                    getPlayer().sendMessage(Component.text("Установленна первая точка арены!"), MessageType.SUCCESS);
                }), "123");
        ItemStack setPos2 = InventoryButton.from(Material.PAPER)
                .setName(Component.text("Установить второй угол арены"))
                .setLore(Component.text("Нажмите ПКМ и стойте там где будет второй угол"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location playerLocation = eventPlayer.getLocation();
                    getArena().setPos2(GameLocation.fromLocation(playerLocation));
                    getPlayer().sendMessage(Component.text("Установленна вторая точка арены!"), MessageType.SUCCESS);
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    if (getArena().getPos1() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установленна первая точка арены!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    if (getArena().getPos2() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установленна вторая точка арены!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    getArena().save();
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.spectatorPositionStage();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, setPos1);
        getPlayer().getPlayer().getInventory().setItem(1, setPos2);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void spectatorPositionStage() {
        setCreationStage(CreationStage.SPECTATOR_POSITION);
        getPlayer().getPlayer().getInventory().clear();
        ItemStack specPos = InventoryButton.from(Material.PAPER)
                .setName(Component.text("Установить спавн спектаторов"))
                .setLore(Component.text("Нажмите ПКМ и стойте там где будет спавн спектаторов"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location playerLocation = eventPlayer.getLocation();
                    getArena().setSpectatorLocation(GameLocation.fromLocation(playerLocation));
                    getPlayer().sendMessage(Component.text("Установленна точка появления спектаторов"), MessageType.SUCCESS);
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, specPos);
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    if (getArena().getSpectatorLocation() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установленна точка появления спектаторов!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    getArena().save();
                    getPlayer().getPlayer().getInventory().clear();
                    spawnersAddStage();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void spawnersAddStage() {
        setCreationStage(CreationStage.SPAWNERS_ADD);
        ItemStack ironSpawner = InventoryButton.from(Material.IRON_BLOCK)
                .setName(Component.text("Добавить спавнер железа"))
                .setLore(Component.text("Нажмите ПКМ где хотите видить спавнер железа"))
                .build((event -> {
                    addSpawner(ItemSpawner.IRON, event);
                }), "123");
        ItemStack goldSpawner = InventoryButton.from(Material.GOLD_BLOCK)
                .setName(Component.text("Добавить спавнер железа"))
                .setLore(Component.text("Нажмите ПКМ где хотите видить спавнер золота"))
                .build((event -> {
                    addSpawner(ItemSpawner.GOLD, event);
                }), "123");
        ItemStack diamondSpawner = InventoryButton.from(Material.DIAMOND_BLOCK)
                .setName(Component.text("Добавить спавнер железа"))
                .setLore(Component.text("Нажмите ПКМ где хотите видить спавнер алмазов"))
                .build((event -> {
                    addSpawner(ItemSpawner.DIAMOND, event);
                }), "123");
        ItemStack emeraldSpawner = InventoryButton.from(Material.EMERALD_BLOCK)
                .setName(Component.text("Добавить спавнер железа"))
                .setLore(Component.text("Нажмите ПКМ где хотите видить спавнер изумрудов"))
                .build((event -> {
                    addSpawner(ItemSpawner.EMERALD, event);
                }), "123");
        ItemStack clear = InventoryButton.from(Material.REDSTONE)
                .setName(Component.text("Осистить спавнера"))
                .setLore(Component.text("Нажмите ПКМ для очистки поставленных спавнеров"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    for (GameSpawner spawner : getArena().getGameSpawners()) {
                        spawner.despawn();
                    }
                    getArena().getGameSpawners().clear();
                    SPBedWars.getInstance().particles.clear();
                    player.sendMessage(Component.text("Успешно очищено все спавнера"), MessageType.SUCCESS);
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    if (getArena().getGameSpawners().size() == 0) {
                        getPlayer().sendMessage(
                                Component.text("Вы не установили ни одного спавнера!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    for (GameSpawner spawner : getArena().getGameSpawners()) {
                        spawner.despawn();
                    }
                    SPBedWars.getInstance().particles.clear();
                    getArena().save();
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.teamsSpawnStage();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, ironSpawner);
        getPlayer().getPlayer().getInventory().setItem(1, goldSpawner);
        getPlayer().getPlayer().getInventory().setItem(2, diamondSpawner);
        getPlayer().getPlayer().getInventory().setItem(3, emeraldSpawner);
        getPlayer().getPlayer().getInventory().setItem(7, clear);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void teamsSpawnStage() {
        setCreationStage(CreationStage.TEAM_SPAWN_ADD);
        getArena().getGameTeams().put("red", new GameTeam(Teams.RED));
        getArena().getGameTeams().put("blue", new GameTeam(Teams.BLUE));
        getArena().getGameTeams().put("green", new GameTeam(Teams.GREEN));
        getArena().getGameTeams().put("yellow", new GameTeam(Teams.YELLOW));
        ItemStack redTeamSpawn = InventoryButton.from(Material.RED_WOOL)
                .setName(Component.text("Добавить спавн красной команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamSpawn(Teams.RED, event);
                }), "123");
        ItemStack greenTeamSpawn = InventoryButton.from(Material.LIME_WOOL)
                .setName(Component.text("Добавить спавн зеленой команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamSpawn(Teams.GREEN, event);
                }), "123");
        ItemStack blueTeamSpawn = InventoryButton.from(Material.BLUE_WOOL)
                .setName(Component.text("Добавить спавн синей команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamSpawn(Teams.BLUE, event);
                }), "123");
        ItemStack yellowTeamSpawn = InventoryButton.from(Material.YELLOW_WOOL)
                .setName(Component.text("Добавить спавн желтой команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamSpawn(Teams.YELLOW, event);
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    for (GameTeam gameTeam : getArena().getGameTeams().values()) {
                        if (gameTeam.getSpawn() == null) {
                            player.sendMessage(Component.text("Спавн команды ")
                                    .append(Component.text(gameTeam.getTeam().getName()))
                                    .append(Component.text("не установлен!")), MessageType.ERROR);
                            return;
                        }
                    }
                    SPBedWars.getInstance().particles.clear();
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.teamsBedStage();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, redTeamSpawn);
        getPlayer().getPlayer().getInventory().setItem(1, blueTeamSpawn);
        getPlayer().getPlayer().getInventory().setItem(2, greenTeamSpawn);
        getPlayer().getPlayer().getInventory().setItem(3, yellowTeamSpawn);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void teamsBedStage() {
        setCreationStage(CreationStage.TEAM_BED_ADD);
        ItemStack redTeamBed = InventoryButton.from(Material.RED_BED)
                .setName(Component.text("Добавить спавн красной команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamBed(Teams.RED, event);
                }), "123");
        ItemStack greenTeamBed = InventoryButton.from(Material.LIME_BED)
                .setName(Component.text("Добавить спавн зеленой команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamBed(Teams.GREEN, event);
                }), "123");
        ItemStack blueTeamBed = InventoryButton.from(Material.BLUE_BED)
                .setName(Component.text("Добавить спавн синей команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamBed(Teams.BLUE, event);
                }), "123");
        ItemStack yellowTeamBed = InventoryButton.from(Material.YELLOW_BED)
                .setName(Component.text("Добавить спавн желтой команды"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    addTeamBed(Teams.YELLOW, event);
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    for (GameTeam gameTeam : getArena().getGameTeams().values()) {
                        if (gameTeam.getBedPos() == null) {
                            player.sendMessage(Component.text("Кровать команды ")
                                    .append(Component.text(gameTeam.getTeam().getName()))
                                    .append(Component.text("не установлен!")), MessageType.ERROR);
                            return;
                        }
                    }
                    SPBedWars.getInstance().particles.clear();
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.shopAddStage();
                    SPBedWars.getInstance().currentCreation.getArena().save();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, redTeamBed);
        getPlayer().getPlayer().getInventory().setItem(1, blueTeamBed);
        getPlayer().getPlayer().getInventory().setItem(2, greenTeamBed);
        getPlayer().getPlayer().getInventory().setItem(3, yellowTeamBed);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void shopAddStage() {
        setCreationStage(CreationStage.SHOP_ADD);
        ItemStack shopItem = InventoryButton.from(Material.VILLAGER_SPAWN_EGG)
                .setName(Component.text("Добавить обычный магазин"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    GameLocation gameLocation = GameLocation.fromLocation(getPlayer().getPlayer().getLocation());
                    GameShop gameShop = new GameShop(gameLocation, GameShopType.NORMAL, "default", null);
                    getArena().getGameShops().add(gameShop);
                    gameShop.spawn();
                }), "123");
        ItemStack upgradeItem = InventoryButton.from(Material.ZOMBIE_VILLAGER_SPAWN_EGG)
                .setName(Component.text("Добавить магазин улучшений"))
                .setLore(Component.text("Нажмите ПКМ где хотите создать спавн команды"))
                .build((event -> {
                    GameLocation gameLocation = GameLocation.fromLocation(getPlayer().getPlayer().getLocation());
                    GameShop gameShop = new GameShop(gameLocation, GameShopType.UPGRADE, "default", null);
                    getArena().getGameShops().add(gameShop);
                    gameShop.spawn();
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    if (getArena().getGameShops().size() < 8) {
                        getPlayer().sendMessage("Нужно как минимум 8 магазинов", MessageType.ERROR);
                        return;
                    }
                    for (GameShop gameShop : getArena().getGameShops()) {
                        gameShop.despawn();
                    }
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.lobbySetStage();
                    SPBedWars.getInstance().currentCreation.getArena().save();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, shopItem);
        getPlayer().getPlayer().getInventory().setItem(1, upgradeItem);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    public void lobbySetStage() {
        setCreationStage(CreationStage.LOBBY_ADD);
        getPlayer().getPlayer().getInventory().clear();
        GameLobby gameLobby = new GameLobby(null, null, null);
        ItemStack setPos1 = InventoryButton.from(Material.BARRIER)
                .setName(Component.text("Установить первый угол лобби"))
                .setLore(Component.text("Нажмите ПКМ там где будет первый угол"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location location = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);
                    if (event.getAction().isLeftClick()) {
                        location.add(0, -1, 0);
                    }
                    gameLobby.setPos1(GameLocation.fromLocation(location));
                    getPlayer().sendMessage(Component.text("Установленна первая точка лобби!"), MessageType.SUCCESS);
                }), "123");
        ItemStack setPos2 = InventoryButton.from(Material.BARRIER)
                .setName(Component.text("Установить второй угол лобби"))
                .setLore(Component.text("Нажмите ПКМ там где будет второй угол"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location location = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);
                    if (event.getAction().isLeftClick()) {
                        location.add(0, -1, 0);
                    }
                    gameLobby.setPos2(GameLocation.fromLocation(location));
                    getPlayer().sendMessage(Component.text("Установленна вторая точка лобби!"), MessageType.SUCCESS);
                }), "123");
        ItemStack lobbyPos = InventoryButton.from(Material.PAPER)
                .setName(Component.text("Установить спавн лобби"))
                .setLore(Component.text("Нажмите ПКМ и стойте там где будет спавн на лобби"))
                .build((event -> {
                    Player eventPlayer = event.getPlayer();
                    Location playerLocation = eventPlayer.getLocation();
                    gameLobby.setSpawn(GameLocation.fromLocation(playerLocation));
                    getPlayer().sendMessage(Component.text("Установленна точка появления в лобби"), MessageType.SUCCESS);
                }), "123");
        ItemStack next = InventoryButton.from(Material.GLOWSTONE_DUST)
                .setName(Component.text("Cледуйщая стадия"))
                .setLore(Component.text("Нажмите ПКМ для перехода на следуйщую стадию "))
                .build((event -> {
                    if (gameLobby.getPos1() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установленна первая точка лобби!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    if (gameLobby.getPos2() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установленна вторая точка лобби!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    if (gameLobby.getSpawn() == null) {
                        getPlayer().sendMessage(
                                Component.text("Не установлен спавн в лобби!"),
                                MessageType.ERROR
                        );
                        return;
                    }
                    getArena().setLobby(gameLobby);
                    getArena().save();
                    getPlayer().getPlayer().getInventory().clear();
                    SPBedWars.getInstance().currentCreation.endEditingArena();
                }), "123");
        getPlayer().getPlayer().getInventory().setItem(0, setPos1);
        getPlayer().getPlayer().getInventory().setItem(1, setPos2);
        getPlayer().getPlayer().getInventory().setItem(2, lobbyPos);
        getPlayer().getPlayer().getInventory().setItem(8, next);
    }

    private void endEditingArena() {
        getArena().setEdit(false);
        getArena().save();
        getPlayer().setEditArena(null);
        getPlayer().save();
        SPBedWars.getInstance().currentCreation = null;
    }

    private void addSpawner(ItemSpawner spawner, PlayerInteractEvent event) {
        GameSpawner gameSpawner = null;
        Location location = event.getClickedBlock().getLocation().add(0.5, 1, 0.5);
        if (event.getAction().isLeftClick()) {
            location.add(0, -1, 0);
        }
        if (spawner.equals(ItemSpawner.IRON)) {
            CuboidHighlighter cuboidHighlighter = new CuboidHighlighter(location, Color.WHITE);
            SPBedWars.getInstance().particles.add(cuboidHighlighter);
            gameSpawner = new GameSpawner(GameLocation.fromLocation(location), spawner, false);
            getPlayer().sendMessage(Component.text("Установлен спавнер железа"), MessageType.SUCCESS);
        }
        if (spawner.equals(ItemSpawner.GOLD)) {
            CuboidHighlighter cuboidHighlighter = new CuboidHighlighter(location, Color.YELLOW);
            SPBedWars.getInstance().particles.add(cuboidHighlighter);
            gameSpawner = new GameSpawner(GameLocation.fromLocation(location), spawner, false);
            getPlayer().sendMessage(Component.text("Установлен спавнер золота"), MessageType.SUCCESS);
        }
        if (spawner.equals(ItemSpawner.DIAMOND)) {
            CuboidHighlighter cuboidHighlighter = new CuboidHighlighter(location, Color.AQUA);
            SPBedWars.getInstance().particles.add(cuboidHighlighter);
            gameSpawner = new GameSpawner(GameLocation.fromLocation(location), spawner, true);
            getPlayer().sendMessage(Component.text("Установлен спавнер алмазов"), MessageType.SUCCESS);
        }
        if (spawner.equals(ItemSpawner.EMERALD)) {
            CuboidHighlighter cuboidHighlighter = new CuboidHighlighter(location, Color.LIME);
            SPBedWars.getInstance().particles.add(cuboidHighlighter);
            gameSpawner = new GameSpawner(GameLocation.fromLocation(location), spawner, true);
            getPlayer().sendMessage(Component.text("Установлен спавнер изумрудов"), MessageType.SUCCESS);
        }
        gameSpawner.create();
        getArena().getGameSpawners().add(gameSpawner);
    }

    public void addTeamSpawn(Teams team, PlayerInteractEvent event) {
        if (event.getAction().isLeftClick()) {
            return;
        }
        Map<String, GameTeam> gameTeamMap = getArena().getGameTeams();
        GameTeam gameTeam = null;
        Location location1 = event.getPlayer().getLocation();
        GameLocation location = GameLocation.fromLocation(location1);
        Component text = Component.text("Установлен спавн команды ");
        CuboidHighlighter cuboidHighlighter = null;
        if (team.equals(Teams.RED)) {
            gameTeam = gameTeamMap.get("red");
            gameTeam.setSpawn(location);
            gameTeamMap.put("red", gameTeam);
            text.append(Component.text("Красных"));
            cuboidHighlighter = new CuboidHighlighter(location1, Color.RED);
        } else if (team.equals(Teams.BLUE)) {
            gameTeam = gameTeamMap.get("blue");
            gameTeam.setSpawn(location);
            gameTeamMap.put("blue", gameTeam);
            cuboidHighlighter = new CuboidHighlighter(location1, Color.BLUE);
            text.append(Component.text("Синих"));
        } else if (team.equals(Teams.GREEN)) {
            gameTeam = gameTeamMap.get("green");
            gameTeam.setSpawn(location);
            gameTeamMap.put("green", gameTeam);
            cuboidHighlighter = new CuboidHighlighter(location1, Color.LIME);
            text.append(Component.text("Зеленых"));
        } else if (team.equals(Teams.YELLOW)) {
            gameTeam = gameTeamMap.get("yellow");
            gameTeam.setSpawn(location);
            gameTeamMap.put("yellow", gameTeam);
            cuboidHighlighter = new CuboidHighlighter(location1, Color.YELLOW);
            text.append(Component.text("Желтых"));
        }
        SPBedWars.getInstance().particles.add(cuboidHighlighter);
        player.sendMessage(text, MessageType.SUCCESS);
        getArena().setGameTeams(gameTeamMap);
    }

    public void addTeamBed(Teams team, PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            player.sendMessage("Нужно ставить кровать а не ломать!", MessageType.ERROR);
            return;
        }
        Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
        TeamBed teamBed = null;
        Map<String, GameTeam> gameTeamMap = getArena().getGameTeams();
        GameTeam gameTeam;
        Component text = Component.text("Установлен кровать команды ");
        if (team.equals(Teams.RED)) {
            gameTeam = gameTeamMap.get("red");
            teamBed = new TeamBed(GameLocation.fromLocation(location), PlayerUtils.getBlockFace(getPlayer().getPlayer()), DyeColor.RED);
            gameTeam.setBedPos(teamBed);
            gameTeamMap.put("red", gameTeam);
            text.append(Component.text("Красных"));
        } else if (team.equals(Teams.BLUE)) {
            gameTeam = gameTeamMap.get("blue");
            teamBed = new TeamBed(GameLocation.fromLocation(location), PlayerUtils.getBlockFace(getPlayer().getPlayer()), DyeColor.BLUE);
            gameTeam.setBedPos(teamBed);
            gameTeamMap.put("blue", gameTeam);
            text.append(Component.text("Синих"));
        } else if (team.equals(Teams.GREEN)) {
            gameTeam = gameTeamMap.get("green");
            teamBed = new TeamBed(GameLocation.fromLocation(location), PlayerUtils.getBlockFace(getPlayer().getPlayer()), DyeColor.LIME);
            gameTeam.setBedPos(teamBed);
            gameTeamMap.put("green", gameTeam);
            text.append(Component.text("Зеленых"));
        } else if (team.equals(Teams.YELLOW)) {
            gameTeam = gameTeamMap.get("yellow");
            teamBed = new TeamBed(GameLocation.fromLocation(location), PlayerUtils.getBlockFace(getPlayer().getPlayer()), DyeColor.YELLOW);
            gameTeam.setBedPos(teamBed);
            gameTeamMap.put("yellow", gameTeam);
            text.append(Component.text("Желтых"));
        }
        player.sendMessage(text, MessageType.SUCCESS);
        teamBed.setBedBlock(getPlayer().getPlayer());
        getArena().setGameTeams(gameTeamMap);
    }
}
