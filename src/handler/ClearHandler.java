package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import result.MessageResult;
import service.ClearService;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles clear operations.
 */
public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();

        try {
            MessageResult result = ClearService.clear();

            int statusCode = 200;

            responseMap.put("message", result.getMessage());

            if (!result.getMessage().equals("Clear succeeded.")) {
                statusCode = 500;
            }

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}