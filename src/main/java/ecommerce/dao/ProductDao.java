package ecommerce.dao;

import ecommerce.models.Product;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class ProductDao {
    private static final String TABLE_NAME = "e-commerce-product-table-v1";
    private static final TableSchema<Product> productSchema = TableSchema.fromBean(Product.class);
    private static DynamoDbEnhancedClient enhancedClient;
    private static DynamoDbTable<Product> productTable;

    public ProductDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        productTable = enhancedClient.table(TABLE_NAME, productSchema);
    }

    public void createProduct(@NonNull Product product) {
        productTable.putItem(product);
    }

    @NonNull
    public Product getProduct(@NonNull String productId) {
        return productTable.getItem(Key.builder()
                .partitionValue(productId)
                .build());
    }
}
