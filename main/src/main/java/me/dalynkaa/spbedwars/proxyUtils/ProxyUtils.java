package me.dalynkaa.spbedwars.proxyUtils;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.proxyUtils.data.ArenaRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.ServerEditRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.ServerInfo;
import me.dalynkaa.spbedwars.proxyUtils.data.ServerRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.game.GameCreationRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.game.GameDeleteRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.game.GameJoinRegistrator;
import me.dalynkaa.spbedwars.proxyUtils.data.game.GameRegistrator;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.config.ArenaConfig;
import me.dalynkaa.spbedwars.utils.config.Config;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import me.dalynkaa.spbedwars.utils.dataclasses.game.GameArena;
import me.dalynkaa.spbedwars.utils.dataclasses.player.TeamPlayer;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProxyUtils {
    private final SPBedWars spBedWars;
    private RedisConnectionWrapper redisConnection;

    public ProxyUtils(SPBedWars spBedWars) {
        this.spBedWars = spBedWars;
        this.redisConnection = new RedisConnectionWrapper(
                spBedWars.getConfig().getString("redis.ip", "127.0.0.1"),
                spBedWars.getConfig().getInt("redis.port", 6379),
                spBedWars.getConfig().getString("redis.password", "")
        );
        //keepRedisConnectionAlive();
        registerServer();
        subscribe();
    }

    public void subscribe() {
        Logger.debug("Subscribing to channels: " + Arrays.toString(Channels.values()));
        Thread thread = new Thread(() -> {
            Jedis subscriberJedis = null;
            try {
                subscriberJedis = redisConnection.getJedis();
                subscriberJedis.subscribe(new MyJedisSubscriber(),
                        Channels.SERVER_REGISTRATION_REQUEST.getChannel(),
                        Channels.GAME_REGISTRATION_REQUEST.getChannel(),
                        Channels.GAME_JOIN.getChannel(),
                        Channels.SERVER_EDIT.getChannel(),
                        Channels.GAME_CREATION_REQUEST.getChannel(),
                        Channels.GAME_DELETE_REQUEST.getChannel());
            } catch (JedisConnectionException e) {
                Logger.debug("Redis connection error");
                if (subscriberJedis != null) {
                    returnRes(subscriberJedis);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    Logger.error("Error while sleeping thread");
                }
                subscribe();
            }
        });
        thread.start();
    }

    private GameRegistrator getRegistration(BWGame game) {
        List<TeamPlayer> activePlayers = new ArrayList<>();
        for (TeamPlayer teamPlayer : game.getPlayers()) {
            if (!teamPlayer.isLoose()) {
                activePlayers.add(teamPlayer);
            }
        }
        return new GameRegistrator(Config.getServerId(), game.getGameId(), game.getArena().getArenaName(), game.getArena().isEdit(), game.getGameStage(), game.getPlayers(), activePlayers, game.getArena().getArenaType());
    }

    public void registerGame(BWGame game, boolean edit) {
        GameRegistrator registration = getRegistration(game);
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_REGISTRATION.getChannel(), registration.toJson());
        returnRes(jedis);
    }

    public void updateGame(BWGame game) {
        GameRegistrator registration = getRegistration(game);
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_UPDATE.getChannel(), registration.toJson());
        returnRes(jedis);
    }

    public void registerArena(GameArena gameArena) {
        ArenaRegistrator arenaRegistrator = new ArenaRegistrator(Config.getServerId(), gameArena.getArenaType(), gameArena.getArenaName(), gameArena.getId());
        String json = arenaRegistrator.toJson();
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.ARENA_REGISTRATION.getChannel(), json);
        returnRes(jedis);
    }

    public void unregisterArena() {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.ARENA_UNREGISTRATION.getChannel(), Config.getServerId().toString());
        returnRes(jedis);
    }

    private void registerServer() {
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", Bukkit.getPort());
        String json = new ServerRegistrator(Config.getServerId(), Config.getServerName(), Config.getServerType(), serverInfo, Config.getServerEdit()).toJson();
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.SERVER_REGISTRATION.getChannel(), json);
        returnRes(jedis);
        for (String config : ArenaConfig.getAllArenasConfig()) {
            GameArena gameArena = GameArena.getByID(UUID.fromString(config));
            gameArena.loadArenaPlayList();
            registerArena(gameArena);
        }
    }

    public void unregisterServer() {
        unregisterArena();
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.SERVER_UNREGISTRATION.getChannel(), Config.getServerId().toString());
        returnRes(jedis);
    }

    public void unregisterGame(UUID gameId) {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_UNREGISTRATION.getChannel(), Config.getServerId().toString() + ":" + gameId.toString());
        returnRes(jedis);
    }

    public void sendPlayerToServer(UUID playerUUID, String serverName) {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_JOIN_REQUEST.getChannel(), playerUUID.toString() + ":" + serverName);
        returnRes(jedis);
    }


    private class MyJedisSubscriber extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            if (channel.equals(Channels.SERVER_REGISTRATION_REQUEST.getChannel())) {
                registerServer();
//                for (BWGame game : spBedWars.activeGames.values()) {
//                    registerGame(game);
//                }
            } else if (channel.equals(Channels.GAME_JOIN.getChannel())) {
                GameJoinRegistrator gameJoinRegistrator = GameJoinRegistrator.fromJson(message);
                Logger.debug("GameJoinRegistrator: " + gameJoinRegistrator.toJson());
                if (gameJoinRegistrator.getServerId().equals(Config.getServerId())) {
                    if (gameJoinRegistrator.getGameId() == null && gameJoinRegistrator.getJoinType() == GameJoinRegistrator.JoinType.EDIT) {
                        SPBedWars.getInstance().gameJoinTemp.put(gameJoinRegistrator.getUserUUID(), gameJoinRegistrator);
                        sendPlayerToServer(gameJoinRegistrator.getUserUUID(), Config.getServerName());
                        return;
                    }
                    if (gameJoinRegistrator.getGameId() != null) {
                        SPBedWars.getInstance().gameJoinTemp.put(gameJoinRegistrator.getUserUUID(), gameJoinRegistrator);
                        sendPlayerToServer(gameJoinRegistrator.getUserUUID(), Config.getServerName());
                        return;
                    }
                }
            } else if (channel.equals(Channels.SERVER_EDIT.getChannel())) {
                ServerEditRegistrator serverEditRegistrator = ServerEditRegistrator.fromJson(message);
                if (serverEditRegistrator.getServerId().equals(Config.getServerId())) {
                    Config.setServerEdit(serverEditRegistrator.isEdit());
                    if (serverEditRegistrator.isEdit()) {
                        for (BWGame game : spBedWars.activeGames.values()) {
                            unregisterGame(game.getGameId());
                        }
                        spBedWars.activeGames.clear();
                    } else {
                        SPBedWars.getInstance().editsGames.clear();
                    }
                    Logger.info("Server edit set to " + serverEditRegistrator.isEdit());
                }
            } else if (channel.equals(Channels.GAME_CREATION_REQUEST.getChannel())) {
                GameCreationRegistrator gameCreationRegistrator = GameCreationRegistrator.fromJson(message);
                if (!gameCreationRegistrator.getServerId().equals(Config.getServerId())) {
                    return;
                }
                Logger.debug("Game creation request");
                Bukkit.getScheduler().runTask(SPBedWars.getInstance(), () -> {
                    BWGame game = BWGame.createNewGame(gameCreationRegistrator.getArenaId(), gameCreationRegistrator.getArenaType());
                    GameJoinRegistrator gameJoinRegistrator = new GameJoinRegistrator(gameCreationRegistrator.getRequestedPlayer(), game.getGameId(), Config.getServerId(), GameJoinRegistrator.JoinType.JOIN);
                    gameJoinRegistrator.setGameId(game.getGameId());
                    gameJoinRegistrator.setJoinType(GameJoinRegistrator.JoinType.JOIN);
                    sendPlayerToServer(gameJoinRegistrator.getUserUUID(), Config.getServerName());
                    //BungeeMessanging.sendServer(BPlayer.getByUUID(gameJoinRegistrator.getUserUUID()), Config.getServerName());
                    SPBedWars.getInstance().gameJoinTemp.put(gameJoinRegistrator.getUserUUID(), gameJoinRegistrator);
                });
            } else if (channel.equals(Channels.GAME_DELETE_REQUEST.getChannel())) {
                GameDeleteRegistrator gameDeleteRegistrator = GameDeleteRegistrator.fromJson(message);
                if (!gameDeleteRegistrator.getServerId().equals(Config.getServerId())) {
                    return;
                }
                if (spBedWars.activeGames.containsKey(gameDeleteRegistrator.getGameId())) {
                    BWGame game = spBedWars.activeGames.get(gameDeleteRegistrator.getGameId());
                    game.getPlayers().forEach(teamPlayer -> teamPlayer.leaveGame(game, true));
                    game.removeCurrentGame();
                    Logger.debug("Game deleted");
                }
            } else {
                Logger.debug("Message from " + channel + ": " + message);

            }
        }
    }

    public void close() {
        redisConnection.close();
    }

    private void returnRes(Jedis jedis) {
        if (jedis.isBroken()) {
            redisConnection.getJedisPool().returnBrokenResource(jedis);
        } else {
            redisConnection.getJedisPool().returnResource(jedis);
        }
    }
}
