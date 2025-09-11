package ecommerce.config;

import ecommerce.s3.ECommerceS3Client;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

public class AppConfig {
    private static final SdkHttpClient httpClient = UrlConnectionHttpClient.builder().build();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final S3Presigner s3Presigner = S3Presigner.create();

    private static final ECommerceS3Client ecommerceS3Client = new ECommerceS3Client(s3Presigner);

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
    public static SdkHttpClient getHttpClient() {
        return httpClient;
    }
    public static ECommerceS3Client getEcommerceS3Client() { return ecommerceS3Client; }
}
