package watson;

import com.google.common.base.Strings;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationModel;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationModels;

import service.WatsonTranslatorService;

/**
 * Watson翻訳APIの翻訳モデル(List models)の処理クラス
 */
public class SupportModel {

    public String getMessage() {
        return getMessage(null);
    }
    public String getMessage(String lang) {
        LanguageTranslator service = WatsonTranslatorService.getService();
        TranslationModels models = service.listModels(null).execute();

        StringBuilder buf = new StringBuilder();
        for (TranslationModel model : models.getModels()) {
            String srcLang = model.getSource();
            String targetLang = model.getTarget();
            String domain = model.getDomain();
            
            if (!Strings.isNullOrEmpty(lang) && !srcLang.equals(lang)) {
                continue;
            }
            buf.append(srcLang + "→" + targetLang + " (" + domain + ")");
            buf.append(System.lineSeparator());
        }
        return buf.toString();
    }
 }
