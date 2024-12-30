package ecommerce.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.pojo.GetProductResponse;
import ecommerce.pojo.Response;

public class ECommerceControlPlaneHandler implements
        RequestHandler<APIGatewayProxyRequestEvent, Response> {

  @Override
  public Response handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    logger.log("Path: " + event.getPath());
    return GetProductResponse.builder().build();
  }
}
