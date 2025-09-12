package ecommerce.dao;

import ecommerce.models.Product;
import ecommerce.models.ProductImage;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.stream.Collectors;

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

    @NonNull
    public List<ProductImage> getProductImages(@NonNull String productId) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(productId)
                .build());

        return productImageTable
                .query(queryConditional)
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    public void deleteProductImage(@NonNull String productId, @NonNull String id) {
        productImageTable.deleteItem(Key.builder()
                .partitionValue(productId)
                .sortValue(id)
                .build());
    }
}
