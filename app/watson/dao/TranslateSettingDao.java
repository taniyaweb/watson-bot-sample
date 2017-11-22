package watson.dao;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.RedisService;
import watson.entity.TranslateSetting;
import play.Logger;
import redis.clients.jedis.Jedis;

/**
 * TranslateSettingのデータの取得、永続化処理(Redisへ保存)
 */
public class TranslateSettingDao {

    public static TranslateSetting get(int topicId) {
        Jedis jedis = null;
        TranslateSetting setting = null;
        try {
            jedis = RedisService.getJedis();
            String strTopicId = String.valueOf(topicId);
            
            if (jedis.exists(strTopicId)) {
                String json = jedis.get(strTopicId);
                ObjectMapper objectMapper = new ObjectMapper();
                setting = objectMapper.readValue(json, TranslateSetting.class);
            }
            
            // Redisから値が取得できなかった場合
            if (setting == null) {
                setting = new TranslateSetting();
                setting.setTopicId(topicId);
            }
        } catch (IOException e) {
            Logger.error("parse error json to object[key = " + topicId + "]", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return setting;
    }
    
    public static void save(TranslateSetting setting) {
        Jedis jedis = null;
        try {
            // object to json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(setting);
            
            // redisに保存
            jedis = RedisService.getJedis();
            jedis.set(String.valueOf(setting.getTopicId()), json);
        } catch (JsonProcessingException e) {
            Logger.error("parse error json to object[key = " + setting.getTopicId() + "]", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
}
