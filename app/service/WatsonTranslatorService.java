package service;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;

/**
 * Watson翻訳API接続のサービスクラス
 */
public class WatsonTranslatorService {

    public static LanguageTranslator getService() {
        LanguageTranslator service = new LanguageTranslator();
        service.setUsernameAndPassword(EnvProperty.getWatosonUsername(), EnvProperty.getWatosonPassword());
        return service;
    }
}
