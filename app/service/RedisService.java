package service;

import java.net.URI;
import java.net.URISyntaxException;

import play.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis接続のサービスクラス
 */
public class RedisService {

    private static JedisPool pool = null;
    
    public static Jedis getJedis() {
        if (pool == null) {
            pool = getPool();
        }
        Jedis jedis = pool.getResource();
        return jedis;
    }
    
    private static JedisPool getPool() {
        JedisPool pool = null;
        try {
            URI redisURI = new URI(EnvProperty.getRedisURL());
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            pool = new JedisPool(poolConfig, redisURI);
        } catch (URISyntaxException e) {
            Logger.error("JedisPoolの生成に失敗", e);
        }
        return pool;
    }
}
