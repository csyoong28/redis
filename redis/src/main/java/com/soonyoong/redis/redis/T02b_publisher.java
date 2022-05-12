package com.soonyoong.redis.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class T02b_publisher {
    public static void main(String[] args) {
        usePublish();
    }

    public static void usePublish() {
        Jedis jPublisher = new Jedis();
        jPublisher.publish("channel", "test message");
    }

}
