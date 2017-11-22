package typetalk;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TypeTalkBotHandlerTest {
    @Test
    public void testExecute() throws IOException {
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        // テスト用のメッセージjsonを読み込み
        byte[] fileContentBytes = Files.readAllBytes(Paths.get("test/typetalk/test_case_request.json"));
        String str = new String(fileContentBytes, StandardCharsets.UTF_8);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(str);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setJsonMessgge(json);
        
        typeTalk.setMessage("translate lang");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage().length(), is(not(0)));
        
        typeTalk.setMessage("translate model");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage().length(), is(not(0)));
        
        typeTalk.setMessage("translate model en");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage().length(), is(not(0)));
        
        typeTalk.setMessage("translate status");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage().length(), is(not(0)));
        
        typeTalk.setMessage("translate off");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), is("set translate off"));
        
        typeTalk.setMessage("translate on");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), is("set translate on"));
        
        typeTalk.setMessage("translate ja on");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), is("set ja(Japanese) on"));
        
        typeTalk.setMessage("translate ja off");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), is("set ja(Japanese) off"));
        
        typeTalk.setMessage("translate aa off");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), startsWith("aa is not support language."));
        
        typeTalk.setMessage("hello");
        hander.execute(typeTalk);
        assertThat(typeTalk.getTopicId(), is(123));
        assertThat(typeTalk.getResponseMessage(), startsWith("(English)"));
    }
    @Test
    public void testIsMatchTranslateLang() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateLang", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate lang");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate a lang");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateStatus() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateStatus", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate status");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate a status");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateOn() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateOn", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate on");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate off");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
        
        typeTalk.setMessage("translate a on");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateOff() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateOff", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate off");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));

        typeTalk.setMessage("translate on");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));

        typeTalk.setMessage("translate a off");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateLangOn() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateLangOn", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate en on");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate en off");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
        
        typeTalk.setMessage("translate on");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateLangOff() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateLangOff", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate en off");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate en on");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
        
        typeTalk.setMessage("translate off");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
    @Test
    public void testIsMatchTranslateModel() throws Exception {
        // privateメソッドのテスト
        TypeTalkBotHandler hander = new TypeTalkBotHandler();
        Method method = TypeTalkBotHandler.class.getDeclaredMethod("isMatchTranslateModel", TypeTalkMessage.class);
        method.setAccessible(true);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setMessage("translate model");
        boolean res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate model en");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(true));
        
        typeTalk.setMessage("translate a model");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
        
        typeTalk.setMessage("translate mode");
        res = (boolean)method.invoke(hander, typeTalk);
        assertThat(res, is(false));
    }
}
