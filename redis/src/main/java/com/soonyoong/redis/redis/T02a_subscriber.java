package com.soonyoong.redis.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class T02a_subscriber {
    public static void main(String[] args) {
        useSubscribe();
    }

    public static void useSubscribe() {
        Jedis jSubscriber = new Jedis();
        jSubscriber.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(message);
            }
        }, "channel");
    }

}
