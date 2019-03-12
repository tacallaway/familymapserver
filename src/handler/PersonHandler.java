package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AuthTokenDao;
import model.AuthToken;
import request.PersonRequest;
import result.PersonAllResult;
import result.PersonResult;
import service.PersonService;

import java.util.HashMap;
import java.util.Map;

public class PersonHandler implements HttpHandler {
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
                    PersonAllResult result = PersonService.getPersons(authToken.getUserName());

                    if (result.getMessage() != null) {
                        responseMap.put("message", result.getMessage());
                        statusCode = 500;
                    } else {
                        ObjectWriter ow = new ObjectMapper().writer();
                        String json = ow.writeValueAsString(result.getData());

                        responseMap.put("data", json);
                    }
                } else {
                    String personId = params[2];

                    PersonRequest personRequest = new PersonRequest(personId);

                    PersonResult result = PersonService.getPerson(personRequest);

                    if (result.getPerson() == null) {
                        responseMap.put("message", "Invalid personID parameter");
                        statusCode = 400;
                    } else {

                        if (!result.getPerson().getDescendant().equals(authToken.getUserName())) {
                            responseMap.put("message", "Requested person does not belong to this user");
                            statusCode = 403;
                        } else {
                            if (result.getMessage() != null) {
                                responseMap.put("message", result.getMessage());
                                statusCode = 500;
                            } else {
                                ObjectWriter ow = new ObjectMapper().writer();
                                String json = ow.writeValueAsString(result.getPerson());

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