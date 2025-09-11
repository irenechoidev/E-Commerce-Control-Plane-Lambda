package ecommerce.dao;

import ecommerce.models.User;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

public class UserDao {
    private final PasswordEncoder passwordEncoder;
    private final DynamoDbTable<User> userTable;

    public UserDao(DynamoDbTable<User> userTable, PasswordEncoder passwordEncoder) {
        this.userTable = userTable;
        this.passwordEncoder = passwordEncoder;
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
