package ecommerce.config;

import ecommerce.dao.ProductDao;
import ecommerce.dao.UserDao;
import ecommerce.models.Product;
import ecommerce.models.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DbConfig {
    private static final String PRODUCT_TABLE_NAME = "e-commerce-product-table-v1";
    private static final String USER_TABLE_NAME = "e-commerce-user-table-v1";

    private static final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DynamoDbClient.builder()
                    .httpClient(AppConfig.getHttpClient())
                    .build())
            .build();

    private static final TableSchema<Product> productSchema = TableSchema.fromBean(Product.class);
    private static final DynamoDbTable<Product> productTable =
            enhancedClient.table(PRODUCT_TABLE_NAME, productSchema);

    private static final TableSchema<User> userSchema = TableSchema.fromBean(User.class);
    private static final DynamoDbTable<User> userTable =
            enhancedClient.table(USER_TABLE_NAME, userSchema);

    private static final ProductDao productDao = new ProductDao(productTable);
    private static final UserDao userDao = new UserDao(userTable, AppConfig.getPasswordEncoder());

    public static ProductDao getProductDao() {
        return productDao;
    }
    public static UserDao getUserDao() {
        return userDao;
    }
}

