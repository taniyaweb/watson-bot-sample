package service;

/**
 * 環境変数の設定値の管理クラス
 */
public class EnvProperty {
    
    /**
     * Redis接続用URL
     */
    public static String getRedisURL() {
        return System.getenv("REDIS_URL");
    }
    /**
     * Watoson接続用ユーザー名
     */
    public static String getWatosonUsername() {
        return System.getenv("WATSON_USER_NAME");
    }
    /**
     * Watoson接続用パスワード
     */
    public static String getWatosonPassword() {
        return System.getenv("WATSON_PASSWORD");
    }
}
