package typetalk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TypeTalkMessageTest {
    @Test
    public void testJsonMessggeParse() throws JsonProcessingException, IOException {
        // テスト用のメッセージjsonを読み込み
        byte[] fileContentBytes = Files.readAllBytes(Paths.get("test/typetalk/test_case_request.json"));
        String str = new String(fileContentBytes, StandardCharsets.UTF_8);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(str);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setJsonMessgge(json);
        
        // メッセージjsonを読み込み後の内容チェック
        assertThat(typeTalk.getTopicId(), is(208));
        assertThat(typeTalk.getPostId(), is(333));
        assertThat(typeTalk.getMessage(), is("Hello"));
    }
    @Test
    public void testJsonMessageGet() throws JsonProcessingException, IOException {
        // テスト用のメッセージjsonを読み込み
        byte[] fileContentBytes = Files.readAllBytes(Paths.get("test/typetalk/test_case_request.json"));
        String str = new String(fileContentBytes, StandardCharsets.UTF_8);
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(str);
        
        TypeTalkMessage typeTalk = new TypeTalkMessage();
        typeTalk.setJsonMessgge(json);
        
        // messageの内容を書き換え
        typeTalk.setResponseMessage("test message");
        
        // 返却用メッセージのjsonを取得
        ObjectNode responsJson = typeTalk.getResponseMessageJson();
        
        // jsonの設定内容をチェック
        String message = responsJson.findPath("message").asText();
        int replyTo = responsJson.findPath("replyTo").asInt();
        assertThat(message, is("test message"));
        assertThat(replyTo, is(333));
    }
}
