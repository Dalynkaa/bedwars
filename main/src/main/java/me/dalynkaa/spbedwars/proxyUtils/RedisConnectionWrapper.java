package me.dalynkaa.spbedwars.proxyUtils;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionWrapper {
    private JedisPool jedisPool;
    private String redisHost;
    private int redisPort;
    private String redisPassword;

    public RedisConnectionWrapper(String redisHost, int redisPort, String redisPassword) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.redisPassword = redisPassword;
        initializeJedisPool();
    }

    private void initializeJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(50);

        jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 5000);
    }

    public Jedis getJedis() {
        try {
            Jedis jedis = jedisPool.getResource();
            Bukkit.getLogger().info(String.valueOf(jedisPool.getCreatedCount()));
            if (!jedis.isConnected()) {
                jedis.close();
                jedis = jedisPool.getResource();
            }
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            initializeJedisPool();
            Jedis jedis = jedisPool.getResource();
            Bukkit.getLogger().info(String.valueOf(jedisPool.getCreatedCount()));
            if (!jedis.isConnected()) {
                jedis.close();
                jedis = jedisPool.getResource();
            }
            return jedis;
        }
    }
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void close() {
        jedisPool.close();
    }
}