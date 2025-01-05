package ecommerce.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ecommerce.models.User;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserDao {
    private static final String TABLE_NAME = "e-commerce-user-table-v1";
    private static final TableSchema<User> userSchema = TableSchema.fromBean(User.class);
    private static DynamoDbEnhancedClient enhancedClient;
    private static PasswordEncoder passwordEncoder;
    private static DynamoDbTable<User> userTable;

    public UserDao() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        userTable = enhancedClient.table(TABLE_NAME, userSchema);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public void createUser(@NonNull User user) {
        String unhashedPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(unhashedPassword);
        user.setPassword(hashedPassword);

        userTable.putItem(user);
    }

    @NonNull
    public User getUser(@NonNull String userId) {
        return userTable.getItem(Key.builder()
                .partitionValue(userId)
                .build());
    }
}
