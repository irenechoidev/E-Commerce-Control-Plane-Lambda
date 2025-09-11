package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.api.Response;
import ecommerce.config.DbConfig;
import ecommerce.dao.ProductDao;
import ecommerce.models.Product;
import lombok.NonNull;

import java.util.Map;

public class ProductService {

    private static final int SUCCESS_STATUS_CODE = 200;

    private static final String QUERY_PARAMS_USER_ID_KEY = "productId";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ProductDao productDao;

    public ProductService() {
        productDao = DbConfig.getProductDao();
    }

    public Response createProduct(@NonNull APIGatewayProxyRequestEvent event) {
        try {
            Product product = objectMapper.readValue(event.getBody(), Product.class);
            productDao.createProduct(product);

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Response getProduct(@NonNull APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParams = event.getQueryStringParameters();
        String productId = queryParams.getOrDefault(QUERY_PARAMS_USER_ID_KEY, "");

        Product product = productDao.getProduct(productId);

        try {
            String serializedProduct = objectMapper.writeValueAsString(product);

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .body(serializedProduct)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
