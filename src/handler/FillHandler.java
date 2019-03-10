package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FillRequest;
import result.MessageResult;
import service.ClearService;
import service.FillService;

import java.util.HashMap;
import java.util.Map;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();

        try {
            String message;
            int statusCode = 200;

            String path = he.getRequestURI().getPath();
            String[] params = path.split("/");

            if (params.length < 3) {
                message = "Missing parameters";
                statusCode = 400;
            } else {
                String username = params[2];

                int numGenerations = (params.length == 3) ? 4 : Integer.parseInt(params[3]);

                ClearService.clearUserData(username);
                MessageResult result = FillService.fill(new FillRequest(username, numGenerations));
                message = result.getMessage();
            }

            responseMap.put("message", message);

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (NumberFormatException e) {
            responseMap.put("message", "Invalid number of generations");
            HandlerUtil.respond(he, responseMap, 400);
        } catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}