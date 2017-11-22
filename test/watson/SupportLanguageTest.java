package watson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

public class SupportLanguageTest {

    @Test
    public void testGetLanguagesMap() {
        // 対応言語一覧の取得
        SupportLanguage supportLanguage = new SupportLanguage();
        Map<String, String> langMap = supportLanguage.getLanguagesMap();
        
        // 取得結果のチェック
        assertThat(langMap.size(), is(not(0)));
        assertThat(langMap.containsKey("en"), is(true));
        assertThat(langMap.get("en"), is("English"));  
    }
    @Test
    public void testExistLanguage() {
        // 対応言語存在チェック
        SupportLanguage supportLanguage = new SupportLanguage();
        
        boolean res = supportLanguage.existLanguage("en");
        assertThat(res, is(true));

        res = supportLanguage.existLanguage("enn");
        assertThat(res, is(false));
    }
}
