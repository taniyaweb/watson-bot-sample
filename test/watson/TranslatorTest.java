package watson;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TranslatorTest {

    @Test
    public void testTranslate() {
        Translator translator = new Translator();
        String message = null;
        String lang = null;
        String res = null;
        
        // 翻訳結果テスト(en→jp)
        message = "hello";
        lang = "ja";
        res = translator.translate(message, lang);
        assertThat(res, is("こんにちは"));

        // 翻訳結果テスト(en→en)
        message = "hello";
        lang = "en";
        res = translator.translate(message, lang);
        assertThat(res, is("hello"));
    }
}
