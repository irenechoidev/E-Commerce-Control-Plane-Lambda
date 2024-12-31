package ecommerce.dao;

import ecommerce.models.User;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserDao {
    private static final String TABLE_NAME = "e-commerce-user-table-v1";
    private static final TableSchema<User> userSchema = TableSchema.fromBean(User.class);
    private static DynamoDbEnhancedClient enhancedClient;

    public UserDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    public void createUser(@NonNull User user) {
        DynamoDbTable<User> userTable = enhancedClient.table(TABLE_NAME, userSchema);
        userTable.putItem(user);
    }
}
