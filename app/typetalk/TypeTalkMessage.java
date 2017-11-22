package typetalk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

/**
 * TypeTalkのJsonメッセージの保持、json変換用クラス
 */
public class TypeTalkMessage {

    private int topicId;
    private int postId;
    private String lang;
    private String message;
    private String responseMessage;
    
    public void setJsonMessgge(JsonNode json) {
        topicId = json.findPath("topic").findPath("id").asInt();
        postId = json.findPath("post").findPath("id").asInt();
        message = json.findPath("post").findPath("message").asText();
        
        // メンション付きの場合は、messageのメッセージ本文の先頭に<@bot名 >が付いているので除去する
        if (message.startsWith("@")) {
            message = message.substring(message.indexOf(" "), message.length());
        }
    }
    public int getTopicId() {
        return topicId;
    }
    public int getPostId() {
        return postId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public ObjectNode getResponseMessageJson() {
        ObjectNode json = Json.newObject();
        json.put("message", responseMessage);
        json.put("replyTo", postId);
        return json;
    }
}
