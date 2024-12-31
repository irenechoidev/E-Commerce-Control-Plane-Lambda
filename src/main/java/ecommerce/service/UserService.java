package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.api.CreateUserResponse;
import ecommerce.dao.UserDao;
import ecommerce.models.User;
import lombok.NonNull;

public class UserService {

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
}
