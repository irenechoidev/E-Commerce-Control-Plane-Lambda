package ecommerce.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.api.CreateProductImageResponseDto;
import ecommerce.api.ImageDto;
import ecommerce.api.Response;
import ecommerce.config.AppConfig;
import ecommerce.config.DbConfig;
import ecommerce.dao.ProductDao;
import ecommerce.models.Product;
import ecommerce.models.ProductImage;
import ecommerce.s3.ECommerceS3Client;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductService {

    private static final int SUCCESS_STATUS_CODE = 200;

    private static final String QUERY_PARAMS_ID_KEY = "id";
    private static final String QUERY_PARAMS_PRODUCT_ID_KEY = "productId";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductDao productDao;
    private final ECommerceS3Client eCommerceS3Client;

    public ProductService() {
        productDao = DbConfig.getProductDao();
        eCommerceS3Client = AppConfig.getEcommerceS3Client();
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
        String productId = queryParams.getOrDefault(QUERY_PARAMS_PRODUCT_ID_KEY, "");

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

    public Response createProductImage(@NonNull APIGatewayProxyRequestEvent event) {
        try {
            ProductImage image = objectMapper.readValue(event.getBody(), ProductImage.class);
            String imageKey = UUID.randomUUID().toString();
            image.setImageKey(imageKey);

            String preSignedUrl = eCommerceS3Client.createPreSignedUrl(imageKey);
            productDao.createProductImage(image);

            CreateProductImageResponseDto responseDto = CreateProductImageResponseDto.builder()
                    .preSignedUrl(preSignedUrl)
                    .build();

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .body(objectMapper.writeValueAsString(responseDto))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Response getProductImages(@NonNull APIGatewayProxyRequestEvent event) {
        try {
            Map<String, String> queryParams = event.getQueryStringParameters();
            String productId = queryParams.getOrDefault(QUERY_PARAMS_PRODUCT_ID_KEY, "");

            List<ProductImage> images = productDao.getProductImages(productId);
            List<ImageDto> imageDtoList = images.stream()
                    .map(image -> ImageDto.builder()
                            .id(image.getId())
                            .position(image.getPosition())
                            .url(eCommerceS3Client.getPreSignedUrl(image.getImageKey()))
                            .build())
                    .toList();

            return Response.builder()
                    .statusCode(SUCCESS_STATUS_CODE)
                    .body(objectMapper.writeValueAsString(imageDtoList))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Response deleteProductImage(@NonNull APIGatewayProxyRequestEvent event) {
        Map<String, String> queryParams = event.getQueryStringParameters();
        String id = queryParams.getOrDefault(QUERY_PARAMS_ID_KEY, "");
        String productId = queryParams.getOrDefault(QUERY_PARAMS_PRODUCT_ID_KEY, "");

        productDao.deleteProductImage(productId, id);

        return Response.builder()
                .statusCode(SUCCESS_STATUS_CODE)
                .build();
    }
}
