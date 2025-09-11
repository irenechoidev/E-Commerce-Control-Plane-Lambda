package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import ecommerce.api.Response;
import ecommerce.config.AppConfig;
import ecommerce.s3.ECommerceS3Client;
import lombok.NonNull;

import java.util.Map;

public class TestService {
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final String QUERY_PARAMS_FILE_KEY = "fileName";
    private final ECommerceS3Client eCommerceS3Client;

    public TestService() {
        this.eCommerceS3Client = AppConfig.getEcommerceS3Client();
    }

    public Response testPutObject(@NonNull APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParams = event.getQueryStringParameters();
        String key = queryParams.getOrDefault(QUERY_PARAMS_FILE_KEY, "");

        eCommerceS3Client.testUploadImage(key);
        return Response.builder()
                .statusCode(SUCCESS_STATUS_CODE)
                .build();
    }
}
