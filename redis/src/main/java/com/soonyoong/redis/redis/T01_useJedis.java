package com.soonyoong.redis.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class T01_useJedis {
    public static void main(String[] args) {
//        useString();
//        useList();
//        useSets();
//        useHashes();
//        useTransaction();
        usePipeline();
    }
    
    public static void useString() {
        Jedis jedis = new Jedis();
        jedis.set("events/city/rome", "32,15,223,828");
        String cachedResponse = jedis.get("events/city/rome");
        System.out.println(cachedResponse);
    }
    
    public static void useList() {
        Jedis jedis = new Jedis();
        jedis.lpush("queue#tasks", "firstTask");
        jedis.lpush("queue#tasks", "secondTask");
        String task = jedis.rpop("queue#tasks");
        System.out.println(task);
    }
    
    public static void useSets() {
        Jedis jedis = new Jedis();
        jedis.sadd("nicknames", "nickname#1");
        jedis.sadd("nicknames", "nickname#2");
        jedis.sadd("nicknames", "nickname#1");

        Set<String> nicknames = jedis.smembers("nicknames");
        boolean exists = jedis.sismember("nicknames", "nickname#1");
        System.out.println(nicknames);
        System.out.println(exists);
    }
    
    public static void useHashes() {
        Jedis jedis = new Jedis();
        jedis.hset("user#1", "name", "Peter");
        jedis.hset("user#1", "job", "politician");
                
        String name = jedis.hget("user#1", "name");
                
        Map<String, String> fields = jedis.hgetAll("user#1");
        String job = fields.get("job");
        System.out.println(fields);    
    }
    
    public static void useSortedSets() {
        Jedis jedis = new Jedis();
        String key = "ranking";
        Map<String, Double> scores = new HashMap<>();

        scores.put("PlayerOne", 3000.0);
        scores.put("PlayerTwo", 1500.0);
        scores.put("PlayerThree", 8200.0);

        scores.entrySet().forEach(playerScore -> {
            jedis.zadd(key, playerScore.getValue(), playerScore.getKey());
        });
  
        String player = jedis.zrevrange("ranking", 0, 1).iterator().next();
        long rank = jedis.zrevrank("ranking", "PlayerOne");
    }
    
    public static void  useTransaction() {
        Jedis jedis = new Jedis();
        String friendsPrefix = "friends#";
        String userOneId = "4352523";
        String userTwoId = "5552321";

        Transaction t = jedis.multi();
        t.sadd(friendsPrefix + userOneId, userTwoId);
        t.sadd(friendsPrefix + userTwoId, userOneId);
        t.exec();
    }
    
    public static void usePipeline() {
        Jedis jedis = new Jedis();
        String userOneId = "4352523";
        String userTwoId = "4849888";

        Pipeline p = jedis.pipelined();
        p.sadd("searched#" + userOneId, "paris");
        p.zadd("ranking", 126, userOneId);
        p.zadd("ranking", 325, userTwoId);
        Response<Boolean> pipeExists = p.sismember("searched#" + userOneId, "paris");
        Response<Set<String>> pipeRanking = p.zrange("ranking", 0, -1);
        p.sync();
        
        System.out.println(pipeExists.get());
        System.out.println(pipeRanking.get());
    }
    
}
