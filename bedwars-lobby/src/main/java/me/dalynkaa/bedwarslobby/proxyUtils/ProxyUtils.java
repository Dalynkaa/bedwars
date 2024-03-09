package me.dalynkaa.bedwarslobby.proxyUtils;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameJoinRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.GameRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerEditRegistrator;
import me.dalynkaa.bedwarslobby.proxyUtils.data.registrators.ServerRegistrator;
import me.dalynkaa.bedwarslobby.utils.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Arrays;
import java.util.UUID;

public class ProxyUtils {
    private final SPBedWarsLobby spBedWars;
    private RedisConnectionWrapper redisConnection;

    public ProxyUtils(SPBedWarsLobby spBedWars) {
        this.spBedWars = spBedWars;
        this.redisConnection = new RedisConnectionWrapper(
                spBedWars.getConfig().getString("redis.ip", "127.0.0.1"),
                spBedWars.getConfig().getInt("redis.port", 6379),
                spBedWars.getConfig().getString("redis.password", "")
        );
        //keepRedisConnectionAlive();
        requerstServerRegistration();
        subscribe();
    }

    public void subscribe() {
        Logger.debug("Subscribing to channels: " + Arrays.toString(Channels.values()));
        Thread thread = new Thread(() -> {
            Jedis subscriberJedis = null;
            try {
                subscriberJedis = redisConnection.getJedis();
                subscriberJedis.subscribe(new MyJedisSubscriber(),
                        Channels.SERVER_REGISTRATION.getChannel(),
                        Channels.GAME_REGISTRATION.getChannel(),
                        Channels.GAME_JOIN.getChannel(),
                        Channels.GAME_UPDATE.getChannel(),
                        Channels.SERVER_UNREGISTRATION.getChannel(),
                        Channels.GAME_UNREGISTRATION.getChannel());
            } catch (JedisConnectionException e) {
                Logger.debug("Redis connection error");
                if (subscriberJedis != null) {
                    returnRes(subscriberJedis);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                subscribe();
            }
        });
        thread.start();
    }

    public void requerstServerRegistration() {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.SERVER_REGISTRATION_REQUEST.getChannel(), "request");
        returnRes(jedis);
    }

    public void requestGameRegistration() {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_REGISTRATION_REQUEST.getChannel(), "request");
        returnRes(jedis);
    }

    public void requestGameRegistrationServer(UUID serverId) {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.GAME_REGISTRATION_REQUEST.getChannel(), serverId.toString());
        returnRes(jedis);
    }

    public void requestGameJoin(GameRegistrator game, BPlayer bPlayer, GameJoinRegistrator.JoinType joinType) {
        Jedis jedis = redisConnection.getJedis();
        GameJoinRegistrator gameJoinRegistrator = new GameJoinRegistrator(bPlayer.getUuid(), game.getGameId(), game.getServerID(), joinType);
        jedis.publish(Channels.GAME_JOIN.getChannel(), gameJoinRegistrator.toJson());
        returnRes(jedis);
    }

    public void setServerEdit(UUID serverId, boolean edit) {
        Jedis jedis = redisConnection.getJedis();
        jedis.publish(Channels.SERVER_EDIT.getChannel(), new ServerEditRegistrator(serverId, edit).toJson());
        returnRes(jedis);
        for (ServerRegistrator serverRegistrator : spBedWars.servers.values()) {
            if (serverRegistrator.getServerId().equals(serverId)) {
                serverRegistrator.setEdit(edit);
                break;
            }
        }
    }

    private class MyJedisSubscriber extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            Logger.debug("Message from " + channel + ": " + message);
            if (channel.equals(Channels.SERVER_REGISTRATION.getChannel())) {
                ServerRegistrator serverRegistrator = ServerRegistrator.fromJson(message);
                Logger.info(ServerRegistrator.fromJson(message).getServerName() + " registered");
                spBedWars.servers.put(serverRegistrator.getServerId(), serverRegistrator);
            } else if (channel.equals(Channels.SERVER_UNREGISTRATION.getChannel())) {
                Logger.info(SPBedWarsLobby.getInstance().servers.get(UUID.fromString(message)).getServerName() + " unregistered");
                spBedWars.servers.remove(UUID.fromString(message));
            } else if (channel.equals(Channels.GAME_REGISTRATION.getChannel())) {
                GameRegistrator gameRegistrator = GameRegistrator.fromJson(message);
                UUID serverId = gameRegistrator.getServerID();
                if (spBedWars.servers.containsKey(serverId)) {
                    spBedWars.servers.get(serverId).addGame(gameRegistrator);
                    Logger.info("Game " + gameRegistrator.getGameId() + " registered");
                }
            } else if (channel.equals(Channels.GAME_UNREGISTRATION.getChannel())) {
                String[] splited = message.split(":");
                UUID serverId = UUID.fromString(splited[0]);
                UUID gameId = UUID.fromString(splited[1]);
                Logger.info("Game " + gameId + " unregistered");
                if (spBedWars.servers.containsKey(serverId)) {
                    spBedWars.servers.get(serverId).removeGame(gameId);
                }
            } else if (channel.equals(Channels.GAME_UPDATE.getChannel())) {
                GameRegistrator gameRegistrator = GameRegistrator.fromJson(message);
                Logger.info("Game " + gameRegistrator.getGameId() + " updated");
                UUID serverId = gameRegistrator.getServerID();
                if (spBedWars.servers.containsKey(serverId)) {
                    if (spBedWars.servers.get(serverId).getGames() != null) {
                        spBedWars.servers.get(serverId).updateGame(gameRegistrator);
                    }
                }
            }
        }
    }

    public void close() {
        redisConnection.close();
    }

    public void returnRes(Jedis jedis) {
        if (jedis.isBroken()) {
            redisConnection.getJedisPool().returnBrokenResource(jedis);
        } else {
            redisConnection.getJedisPool().returnResource(jedis);
        }
    }
}
