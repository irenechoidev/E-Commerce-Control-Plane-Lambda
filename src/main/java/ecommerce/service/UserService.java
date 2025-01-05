package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.api.CreateUserResponse;
import ecommerce.api.GetUserResponse;
import ecommerce.dao.UserDao;
import ecommerce.models.User;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public class UserService {

    private static final String QUERY_PARAMS_USER_ID_KEY = "userId";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public CreateUserResponse createUser(@NonNull APIGatewayProxyRequestEvent event) {
        try {
            User user = objectMapper.readValue(event.getBody(), User.class);
            userDao.createUser(user);

            return CreateUserResponse.builder().build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GetUserResponse getUser(@NonNull APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParams = event.getQueryStringParameters();
        String userId = queryParams.getOrDefault(QUERY_PARAMS_USER_ID_KEY, "");

        User user = userDao.getUser(userId);

        return GetUserResponse.builder()
                .user(user)
                .build();
    }
}
