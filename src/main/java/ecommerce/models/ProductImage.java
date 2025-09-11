package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private String id;
    private String imageKey;
    private int position;
    private String productId;

    @DynamoDbPartitionKey
    public String getProductId() {
        return this.productId;
    }

    @DynamoDbSortKey
    public String getId() { return this.id; }
}
