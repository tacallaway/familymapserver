package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler utility class.
 */
public class HandlerUtil {
    public static Map<String, String> getRequestBody(HttpExchange he) throws Exception {
        String requestBody = new BufferedReader(new InputStreamReader(he.getRequestBody())).lines().collect(Collectors.joining("\n"));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(requestBody, Map.class);
    }

    public static Map<String, List<Map<String, String>>> getRequestBodyList(HttpExchange he) throws Exception {
        String requestBody = new BufferedReader(new InputStreamReader(he.getRequestBody())).lines().collect(Collectors.joining("\n"));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(requestBody, Map.class);
    }

    public static void respond(HttpExchange he, Map<String, String> responseMap, int statusCode) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String responseJson = mapper.writeValueAsString(responseMap);

            he.sendResponseHeaders(statusCode, 0);

            PrintWriter writer = new PrintWriter(he.getResponseBody());
            writer.write(responseJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
