package typetalk;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import watson.SupportLanguage;
import watson.SupportModel;
import watson.Translator;
import watson.dao.TranslateSettingDao;
import watson.entity.TranslateSetting;

/**
 * TypeTalk翻訳ボットの制御クラス
 */
public class TypeTalkBotHandler {

    /**
     * チャットメッセージからコマンドを判定し、処理呼び出し
     * translate lang 識別可能言語一覧の表示
     * translate model <lang> 翻訳モデル一覧の表示
     * translate status 現在の翻訳設定を表示
     * translate on 翻訳処理を有効化
     * translate <lang> on 指定言語の翻訳設定を追加
     * translate <lang> off 指定言語の翻訳設定を削除
     * 上記以外：翻訳設定に基づいて、翻訳結果を取得
     * @param typeTalk
     */
    public TypeTalkMessage execute(TypeTalkMessage typeTalk) {
                
        if (isMatchTranslateLang(typeTalk)) {
            return getTranslateLang(typeTalk);
            
        } else if (isMatchTranslateModel(typeTalk)) {
            return getTranslateModel(typeTalk);
        
        } else if (isMatchTranslateStatus(typeTalk)) {
            return getTranslateStatus(typeTalk);
        
        } else if (isMatchTranslateOn(typeTalk)) {
            return enableTranslation(typeTalk);
        
        } else if (isMatchTranslateOff(typeTalk)) {
            return disableTranslation(typeTalk);
        
        } else if (isMatchTranslateLangOn(typeTalk)) {
            return addTranslationLang(typeTalk);
        
        } else if (isMatchTranslateLangOff(typeTalk)) {
            return removeTranslationLang(typeTalk);
        
        } else {
            return getTranslation(typeTalk);
        }
    }
    private boolean isMatchTranslateLang(TypeTalkMessage typeTalk) {
        return typeTalk.getMessage().startsWith("translate lang");
    }
    private boolean isMatchTranslateStatus(TypeTalkMessage typeTalk) {
        return typeTalk.getMessage().startsWith("translate status");
    }
    private boolean isMatchTranslateOn(TypeTalkMessage typeTalk) {
        return typeTalk.getMessage().startsWith("translate on");
    }
    private boolean isMatchTranslateOff(TypeTalkMessage typeTalk) {
        return typeTalk.getMessage().startsWith("translate off");
    }
    private boolean isMatchTranslateLangOn(TypeTalkMessage typeTalk) {
        String regex = "^translate (?<lang>.*) on$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(typeTalk.getMessage());
        if (m.find()) {
            String lang = m.group("lang");
            typeTalk.setLang(lang);
            return true;
        }
        return false;
    }
    private boolean isMatchTranslateLangOff(TypeTalkMessage typeTalk) {
        String regex = "^translate (?<lang>.*) off$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(typeTalk.getMessage());
        if (m.find()) {
            String lang = m.group("lang");
            typeTalk.setLang(lang);
            return true;
        }
        return false;
    }
    private boolean isMatchTranslateModel(TypeTalkMessage typeTalk) {
        if (typeTalk.getMessage().startsWith("translate model")) {
            String regex = "^translate model (?<lang>.*)$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(typeTalk.getMessage());
            if (m.find()) {
                String lang = m.group("lang");
                typeTalk.setLang(lang);
                return true;
            }
            return true;
        }
        return false;
    }
    private TypeTalkMessage getTranslateLang(TypeTalkMessage typeTalk) {
        SupportLanguage supportlanguage = new SupportLanguage();
        String message = supportlanguage.getMessage();
        typeTalk.setResponseMessage(message);
        return typeTalk;
    }
    private TypeTalkMessage getTranslateStatus(TypeTalkMessage typeTalk) {
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        StringBuilder buf = new StringBuilder();
        if (setting.isEnable()) {
            buf.append("translate on" + System.lineSeparator());
        } else {
            buf.append("translate off" + System.lineSeparator());
        }
        SupportLanguage supportLang = new SupportLanguage();
        Map<String, String> langMap = supportLang.getLanguagesMap();
        for (Iterator<String> it = setting.getLang().iterator(); it.hasNext();) {
            String lang = it.next();
            buf.append(lang + "(" + langMap.get(lang) + ") on" + System.lineSeparator());
        }
        if (setting.getLang().size() == 0) {
            buf.append("no lang setting");
        }
        typeTalk.setResponseMessage(buf.toString());
        return typeTalk;
    }
    
    private TypeTalkMessage getTranslateModel(TypeTalkMessage typeTalk) {
        SupportModel supportModel = new SupportModel();
        String message = supportModel.getMessage(typeTalk.getLang());
        typeTalk.setResponseMessage(message);
        return typeTalk;
    }
    private TypeTalkMessage addTranslationLang(TypeTalkMessage typeTalk) {
        SupportLanguage supportLang = new SupportLanguage();
        if (!supportLang.existLanguage(typeTalk.getLang())) {
            typeTalk.setResponseMessage(typeTalk.getLang() + " is not support language.(check with 'translate lang' command)");
            return typeTalk;
        }
        // 言語設定を追加
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        setting.addLang(typeTalk.getLang());
        // 設定を保存
        TranslateSettingDao.save(setting);
        String langName = supportLang.getLanguageName(typeTalk.getLang());
        typeTalk.setResponseMessage("set " + typeTalk.getLang() + "(" + langName + ") on");
        return typeTalk;
    }
    private TypeTalkMessage removeTranslationLang(TypeTalkMessage typeTalk) {
        SupportLanguage supportLang = new SupportLanguage();
        if (!supportLang.existLanguage(typeTalk.getLang())) {
            typeTalk.setResponseMessage(typeTalk.getLang() + " is not support language.(check with 'translate lang' command)");
            return typeTalk;
        }
        // 言語設定を追加
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        setting.removeLang(typeTalk.getLang());
        // 設定を保存
        TranslateSettingDao.save(setting);
        String langName = supportLang.getLanguageName(typeTalk.getLang());
        typeTalk.setResponseMessage("set " + typeTalk.getLang() + "(" + langName + ") off");
        return typeTalk;
    }
    private TypeTalkMessage enableTranslation(TypeTalkMessage typeTalk) {
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        setting.setEnable(true);
        TranslateSettingDao.save(setting);
        typeTalk.setResponseMessage("set translate on");
        return typeTalk;
    }
    private TypeTalkMessage disableTranslation(TypeTalkMessage typeTalk) {
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        setting.setEnable(false);
        TranslateSettingDao.save(setting);
        typeTalk.setResponseMessage("set translate off");
        return typeTalk;
    }
    private TypeTalkMessage getTranslation(TypeTalkMessage typeTalk) {
        TranslateSetting setting = TranslateSettingDao.get(typeTalk.getTopicId());
        if (!setting.isEnable() || setting.getLang().size() == 0) {
            return typeTalk;
        }
        // 設定されている言語の翻訳結果を取得
        StringBuilder buf = new StringBuilder();
        Translator translator = new Translator();
        SupportLanguage supportlanguage = new SupportLanguage();
        for (Iterator<String> it = setting.getLang().iterator(); it.hasNext();) {
            String lang = it.next();
            String translateMessage = translator.translate(typeTalk.getMessage(), lang);
            buf.append("(" + supportlanguage.getLanguageName(lang) + ")" + System.lineSeparator());
            buf.append(translateMessage + System.lineSeparator());
        }
        typeTalk.setResponseMessage(buf.toString());
        return typeTalk;
    }

}
