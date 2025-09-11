package ecommerce.dao;

import ecommerce.models.Product;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public class ProductDao {
    private final DynamoDbTable<Product> productTable;

    public ProductDao(DynamoDbTable<Product> productTable) {
        this.productTable = productTable;
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
