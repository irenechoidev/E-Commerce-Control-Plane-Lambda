package ecommerce;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.api.Response;
import ecommerce.service.ProductService;
import ecommerce.service.UserService;

public class ECommerceControlPlaneHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, Object> {

  private static final String USER_API_PATH = "/api/v1/user";
  private static final String PRODUCT_API_PATH = "/api/v1/product";
  private static final String PRODUCT_IMAGE_API_PATH = "/api/v1/product/image";

  private static final UserService userService = new UserService();
  private static final ProductService productService = new ProductService();

  @Override
  public Object handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("Path: " + event.getPath());

      return switch (event.getPath()) {
          case USER_API_PATH -> handleUserRequest(event);
          case PRODUCT_API_PATH -> handleProductRequest(event);
          case PRODUCT_IMAGE_API_PATH -> handleProductImageRequest(event);
          default -> throw new UnsupportedOperationException("API Path is not supported");
      };
  }

  private Response handleProductImageRequest(APIGatewayProxyRequestEvent event) {
      if (event.getHttpMethod().equals("POST")) {
          return productService.createProductImage(event);
      } else if (event.getHttpMethod().equals("DELETE")) {
          return productService.deleteProductImage(event);
      }

      return productService.getProductImages(event);
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
