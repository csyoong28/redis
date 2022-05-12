package com.soonyoong.redis.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class T03_connectionPool {
    public static void main(String[] args) {
        useConnectionPool();
    }

    public static void useConnectionPool() {
        final JedisPoolConfig poolConfig = buildPoolConfig();

        try (JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379); Jedis jedis = jedisPool.getResource()) {

            // do simple operation to verify that the Jedis resource is working
            // properly
            String key = "key";
            String value = "value";

            jedis.set(key, value);
            String value2 = jedis.get(key);
            System.out.println(value);
            System.out.println(value2);

            // flush Redis
            jedis.flushAll();
        }
    }

    public static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
