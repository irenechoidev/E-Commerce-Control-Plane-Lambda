package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.api.Response;
import ecommerce.dao.UserDao;
import ecommerce.models.User;
import lombok.NonNull;

import java.util.Map;

public class UserService {

    private static final int SUCCESS_STATUS_CODE = 200;

    private static final String QUERY_PARAMS_USER_ID_KEY = "userId";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public Response createUser(@NonNull APIGatewayProxyRequestEvent event) {
        try {
            User user = objectMapper.readValue(event.getBody(), User.class);
            userDao.createUser(user);

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Response getUser(@NonNull APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParams = event.getQueryStringParameters();
        String userId = queryParams.getOrDefault(QUERY_PARAMS_USER_ID_KEY, "");

        User user = userDao.getUser(userId);

        try {
            String serializedUser = objectMapper.writeValueAsString(user);

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .body(serializedUser)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
