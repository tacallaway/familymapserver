package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoadRequest;
import result.MessageResult;
import service.LoadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles load operations.
 */
public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();
        int statusCode = 200;

        try {
            Map<String,List<Map<String, String>>> map = HandlerUtil.getRequestBodyList(he);

            List users = map.get("users");
            List persons = map.get("persons");
            List events = map.get("events");

            LoadRequest request = new LoadRequest(users, persons, events);
            MessageResult result = LoadService.load(request);

            responseMap.put("message", result.getMessage());

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (IOException e) {
            responseMap.put("message", "Invalid request data");
            HandlerUtil.respond(he, responseMap, 400);
        }
        catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}