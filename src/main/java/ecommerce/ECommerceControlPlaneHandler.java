package ecommerce;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.api.Response;
import ecommerce.service.ProductService;
import ecommerce.service.TestService;
import ecommerce.service.UserService;

public class ECommerceControlPlaneHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, Object> {

  private static final String USER_API_PATH = "/api/v1/user";
  private static final String PRODUCT_API_PATH = "/api/v1/product";
  private static final String TEST_API_PATH = "/api/v1/test";

  private static final UserService userService = new UserService();
  private static final ProductService productService = new ProductService();
  private static final TestService testService = new TestService();

  @Override
  public Object handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("Path: " + event.getPath());

    if (event.getPath().equals(USER_API_PATH)) {
        return handleUserRequest(event);
    } else if (event.getPath().equals(PRODUCT_API_PATH)) {
        return handleProductRequest(event);
    } else if (event.getPath().equals(TEST_API_PATH)) {
        return handleTestRequest(event);
    }

    throw new UnsupportedOperationException("API Path is not supported");
  }

  private Response handleTestRequest(APIGatewayProxyRequestEvent event) {
     return testService.testPutObject(event);
  }

  private Response handleProductRequest(APIGatewayProxyRequestEvent event) {
     if (event.getHttpMethod().equals("POST")) {
         return productService.createProduct(event);
     }

     return productService.getProduct(event);
  }

  private Response handleUserRequest(APIGatewayProxyRequestEvent event) {
      if (event.getHttpMethod().equals("POST")) {
        return userService.createUser(event);
      }

      return userService.getUser(event);
  }
}
