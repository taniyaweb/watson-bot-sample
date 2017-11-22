package watson;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SupportModelTest {

    @Test
    public void testGetMessage() {
        // 翻訳モデル一覧の取得
        SupportModel supportModel = new SupportModel();
        String res = supportModel.getMessage();
        assertThat(res, is(startsWith("en→es")));
        
        res = supportModel.getMessage("en");
        assertThat(res, is(startsWith("en→es")));

        res = supportModel.getMessage("enn");
        assertThat(res, is(""));
    }
}
