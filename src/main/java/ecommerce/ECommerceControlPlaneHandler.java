package ecommerce;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.api.CreateUserResponse;
import ecommerce.api.Response;
import ecommerce.service.UserService;

public class ECommerceControlPlaneHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, Response> {

  private static final String USER_API_PATH = "/api/v1/user/";

  private static final UserService userService = new UserService();

  @Override
  public Response handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("Path: " + event.getPath());

    if (event.getPath().equals(USER_API_PATH)) {
        userService.createUser(event);
    }

    return CreateUserResponse.builder().build();
  }
}
