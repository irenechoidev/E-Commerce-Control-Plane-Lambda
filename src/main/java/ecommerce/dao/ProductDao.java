package ecommerce.dao;

import ecommerce.models.Product;
import ecommerce.models.ProductImage;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public class ProductDao {
    private final DynamoDbTable<Product> productTable;
    private final DynamoDbTable<ProductImage> productImageTable;

    public ProductDao(
            DynamoDbTable<Product> productTable,
            DynamoDbTable<ProductImage> productImageTable
    ) {
        this.productTable = productTable;
        this.productImageTable = productImageTable;
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

    public void createProductImage(@NonNull ProductImage productImage) {
        productImageTable.putItem(productImage);
    }
}
