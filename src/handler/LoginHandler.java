package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();
        int statusCode = 200;

        try {
            Map<String,String> map = HandlerUtil.getRequestBody(he);

            LoginRequest loginRequest = new LoginRequest(map.get("userName"), map.get("password"));

            LoginResult loginResult = LoginService.login(loginRequest);

            if (loginResult.getMessage() != null) {
                responseMap.put("message", loginResult.getMessage());

                if (loginResult.getMessage().equals("Invalid credentials.")) {
                    statusCode = 401;
                } else {
                    statusCode = 500;
                }
            } else {
                responseMap.put("authToken", loginResult.getAuthToken());
                responseMap.put("userName", loginResult.getUserName());
                responseMap.put("personID", loginResult.getPersonID());
            }

            HandlerUtil.respond(he, responseMap, statusCode);
        } catch (IOException e) {
            responseMap.put("message", "Invalid request");
            HandlerUtil.respond(he, responseMap, 400);
        }
        catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }
    }
}