package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {

        String requestBody = new BufferedReader(new InputStreamReader(t.getRequestBody())).lines().collect(Collectors.joining("\n"));


        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.readValue(requestBody, Map.class);

        RegisterRequest registerRequest = new RegisterRequest(map.get("userName"), map.get("password"), map.get("email"), map.get("firstName"), map.get("lastName"), map.get("gender"));

        RegisterResult registerResult = RegisterService.register(registerRequest);

        Map<String, String> responseMap = new HashMap<>();

        int statusCode = 200;

        if (registerResult.getMessage() != null) {
            responseMap.put("message", registerResult.getMessage());
            statusCode = 500;
        } else {
            responseMap.put("authToken", registerResult.getAuthToken());
            responseMap.put("userName", registerResult.getUserName());
            responseMap.put("personID", registerResult.getPersonID());
        }

        String responseJson = new ObjectMapper().writeValueAsString(responseMap);

        t.sendResponseHeaders(statusCode, responseJson.length());

        PrintWriter writer = new PrintWriter(t.getResponseBody());
        writer.write(responseJson);
        writer.close();
    }
}
