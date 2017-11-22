package watson;

import java.util.List;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.IdentifiedLanguages;
import com.ibm.watson.developer_cloud.language_translator.v2.model.IdentifyOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Translation;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.service.exception.NotFoundException;

import service.WatsonTranslatorService;

/**
 * Watson翻訳APIの翻訳(Translate)の処理クラス
 */
public class Translator {
    public String translate(String message, String targetLanguage) {
        LanguageTranslator service = WatsonTranslatorService.getService();

        // watson言語識別
        IdentifyOptions options = new IdentifyOptions.Builder().text(message).build();
        IdentifiedLanguages languages = service.identify(options).execute();
        String srclanguage = languages.getLanguages().get(0).getLanguage();
        
        // srcとtargetが同一言語の場合は、翻訳前のメッセージを返却する
        if (srclanguage.equals(targetLanguage)) {
            return message;
        }
        
        // watson翻訳
        String translateMessage = null;
        try {
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                                                .addText(message)
                                                .source(srclanguage)
                                                .target(targetLanguage)
                                                .build();
            TranslationResult translationResult = service.translate(translateOptions).execute();
            List<Translation> translations = translationResult.getTranslations();
            translateMessage = translations.get(0).getTranslation();
        } catch (NotFoundException e) {
            translateMessage = "翻訳モデルが対応していないため、翻訳できません。";
        }

        return translateMessage;
    }
}
