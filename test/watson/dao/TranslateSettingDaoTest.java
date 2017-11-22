package watson.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import watson.entity.TranslateSetting;

public class TranslateSettingDaoTest {

    @Test
    public void testSaveAndGet() {
        TranslateSetting setting = new TranslateSetting();
        setting.setTopicId(123);
        setting.addLang("ja");
        setting.setEnable(false);
        // RedisへSave
        TranslateSettingDao.save(setting);
        
        // RedisからGet
        TranslateSetting res = TranslateSettingDao.get(123);
        assertThat(res.isEnable(), is(false));
        assertThat(res.getLang().size(), is(2));
        assertThat(res.getLang().contains("en"), is(true));
        assertThat(res.getLang().contains("ja"), is(true));
        
    }
}
