package handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.util.HashMap;
import java.util.Map;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();
        int statusCode = 200;

        try {
            Map<String,String> map = HandlerUtil.getRequestBody(he);

            RegisterRequest registerRequest = new RegisterRequest(map.get("userName"), map.get("password"), map.get("email"), map.get("firstName"), map.get("lastName"), map.get("gender"));

            RegisterResult registerResult = RegisterService.register(registerRequest);

            if (registerResult.getMessage() != null) {
                responseMap.put("message", registerResult.getMessage());
                statusCode = 500;
            } else {
                responseMap.put("authToken", registerResult.getAuthToken());
                responseMap.put("userName", registerResult.getUserName());
                responseMap.put("personID", registerResult.getPersonID());
            }

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (JsonParseException e) {
            responseMap.put("message", "Invalid request");
            HandlerUtil.respond(he, responseMap, 400);
        }
        catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}
