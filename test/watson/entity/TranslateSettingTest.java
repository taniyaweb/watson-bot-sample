package watson.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TranslateSettingTest {

    @Test
    public void test() {
        TranslateSetting setting = new TranslateSetting();
        
        // デフォルト値の設定値のチェック
        assertThat(setting.isEnable(), is(true));
        assertThat(setting.getLang().contains("en"), is(true));
        
        setting.addLang("ja");
        assertThat(setting.existLang("ja"), is(true));
        
        setting.removeLang("ja");
        assertThat(setting.existLang("ja"), is(false));
        
        setting.setEnable(false);
        assertThat(setting.isEnable(), is(false));
    }
}
