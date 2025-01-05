package ecommerce;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.api.Response;
import ecommerce.service.UserService;

public class ECommerceControlPlaneHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, Object> {

  private static final String USER_API_PATH = "/api/v1/user/";

  private static final UserService userService = new UserService();

  @Override
  public Object handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("Path: " + event.getPath());

    if (event.getPath().equals(USER_API_PATH)) {
        return handleUserRequest(event).getData();
    }

    throw new UnsupportedOperationException("API Path is not supported");
  }

  private Response handleUserRequest(APIGatewayProxyRequestEvent event) {
      if (event.getHttpMethod().equals("POST")) {
        return userService.createUser(event);
      }

      return userService.getUser(event);
  }
}
