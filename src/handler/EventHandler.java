package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AuthTokenDao;
import model.AuthToken;
import request.EventRequest;
import result.EventAllResult;
import result.EventResult;
import service.EventService;

import java.util.HashMap;
import java.util.Map;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange he) {
        Map<String, String> responseMap = new HashMap<>();
        int statusCode = 200;

        try {
            String path = he.getRequestURI().getPath();
            String[] params = path.split("/");

            Headers headers = he.getRequestHeaders();
            String token = headers.getFirst("authorization");

            AuthToken authToken = AuthTokenDao.getAuthToken(token);

            if (authToken == null) {
                responseMap.put("message", "Invalid auth token");
                statusCode = 401;
            } else {
                if (params.length < 3) {
                    EventAllResult result = EventService.getEvents(authToken.getUserName());

                    if (result.getMessage() != null) {
                        responseMap.put("message", result.getMessage());
                        statusCode = 500;
                    } else {
                        ObjectWriter ow = new ObjectMapper().writer();
                        String json = ow.writeValueAsString(result.getData());

                        responseMap.put("data", json);
                    }
                } else {
                    String eventId = params[2];

                    EventRequest eventRequest = new EventRequest(eventId);

                    EventResult result = EventService.getEvent(eventRequest);

                    if (result.getEvent() == null) {
                        responseMap.put("message", "Invalid eventID parameter");
                        statusCode = 400;
                    } else {

                        if (!result.getEvent().getDescendant().equals(authToken.getUserName())) {
                            responseMap.put("message", "Requested event does not belong to this user");
                            statusCode = 403;
                        } else {
                            if (result.getMessage() != null) {
                                responseMap.put("message", result.getMessage());
                                statusCode = 500;
                            } else {
                                ObjectWriter ow = new ObjectMapper().writer();
                                String json = ow.writeValueAsString(result.getEvent());

                                responseMap.put("data", json);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            responseMap.put("message", e.getMessage());
            HandlerUtil.respond(he, responseMap, 500);
        }

        HandlerUtil.respond(he, responseMap, statusCode);
    }
}