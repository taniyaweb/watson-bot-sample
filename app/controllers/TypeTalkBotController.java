package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import typetalk.TypeTalkBotHandler;
import typetalk.TypeTalkMessage;
import play.mvc.Controller;
import play.mvc.Result;

public class TypeTalkBotController extends Controller {

    public Result call() {
        // TypeTalkのjsonリクエストを取得
        JsonNode json = request().body().asJson();
        if (json == null) {
            return ok();
        }
        TypeTalkMessage message = new TypeTalkMessage();
        message.setJsonMessgge(json);
        
        // TypeTalkBotの処理
        TypeTalkBotHandler handler = new TypeTalkBotHandler();
        handler.execute(message);
        
        // jsonレスポンスを返却
        return ok(message.getResponseMessageJson());
    }
}
