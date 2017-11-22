package watson;

import java.util.HashMap;
import java.util.Map;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.IdentifiableLanguage;
import com.ibm.watson.developer_cloud.language_translator.v2.model.IdentifiableLanguages;

import service.RedisService;
import service.WatsonTranslatorService;
import redis.clients.jedis.Jedis;

/**
 * Watson翻訳APIの識別可能言語(Identify language)の処理クラス
 */
public class SupportLanguage {
    
    private static final String KEY_LANGUAGES_MAP = "languagesMap";

    public String getMessage() {
        Map<String, String> langMap = getLanguagesMap();

        StringBuilder buf = new StringBuilder();
        for (String key : langMap.keySet()) {
            buf.append(key + "(" + langMap.get(key) + ")");
            buf.append(System.lineSeparator());
        }
        return buf.toString();
    }

    public Map<String, String> getLanguagesMap() {
        // Redisから取得
        Jedis jedis = null;
        Map<String, String> langMap = null;
        try {
            jedis = RedisService.getJedis();
            if (jedis.exists(KEY_LANGUAGES_MAP)) {
                langMap = jedis.hgetAll(KEY_LANGUAGES_MAP);
            }
            
            if (langMap == null) {
                LanguageTranslator service = WatsonTranslatorService.getService();
                IdentifiableLanguages languages = service.listIdentifiableLanguages().execute();

                langMap = new HashMap<String, String>();
                for (IdentifiableLanguage lang : languages.getLanguages()) {
                    langMap.put(lang.getLanguage(), lang.getName());
                }
                // Redisへキャッシュ
                jedis.hmset(KEY_LANGUAGES_MAP, langMap);
                jedis.expire(KEY_LANGUAGES_MAP, 3600);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return langMap;
    }
    
    public boolean existLanguage(String language) {
        Map<String, String> langMap = getLanguagesMap();
        return langMap.containsKey(language);
    }
    
    public String getLanguageName(String language) {
        Map<String, String> langMap = getLanguagesMap();
        if (langMap.containsKey(language)) {
            return langMap.get(language);
        }
        return "";
    }
}
