package watson.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * トピック毎の翻訳設定値を保持用クラス
 */
public class TranslateSetting {

    private int topicId;
    private boolean enable;
    private Set<String> langSetting;

    public TranslateSetting() {
        // デフォルト値は、翻訳on、翻訳言語設定"en"
        langSetting = new HashSet<String>();
        langSetting.add("en");
        setEnable(true);
    }
    
    public int getTopicId() {
        return topicId;
    }
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    public boolean existLang(String lang) {
        return langSetting.contains(lang);
    }
    public void addLang(String lang) {
        if (!langSetting.contains(lang)) {
            langSetting.add(lang);
        }
    }
    public void removeLang(String lang) {
        langSetting.remove(lang);
    }
    public Set<String> getLang() {
        return langSetting;
    }    
}
