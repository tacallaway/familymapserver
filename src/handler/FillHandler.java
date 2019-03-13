package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FillRequest;
import result.MessageResult;
import service.ClearService;
import service.FillService;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles fill operations.
 */
public class FillHandler implements HttpHandler {
    /**
     *  PLEASE NOTE THAT SINCE JAVA'S BASIC HttpService DOESN'T HAVE BUILT IN FILE SERVING, THE FOLLOWING METHOD WAS
     *  BORROWED FROM AN ONLINE TUTORIAL.
     */
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();

        try {
            String message;
            int statusCode = 200;

            String path = he.getRequestURI().getPath();
            String[] params = path.split("/");

            if (params.length < 3) {
                message = "Invalid username or generations parameter";
                statusCode = 400;
            } else {
                String username = params[2];

                String numGenerationsString = (params.length == 3) ? "4" : params[3];

                int numGenerations = Integer.parseInt(numGenerationsString);

                ClearService.clearUserData(username);
                MessageResult result = FillService.fill(new FillRequest(username, numGenerations));
                message = result.getMessage();
            }

            responseMap.put("message", message);

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (NumberFormatException e) {
            responseMap.put("message", "Invalid username or generations parameter");
            HandlerUtil.respond(he, responseMap, 400);
        } catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}