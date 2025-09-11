package ecommerce.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class ECommerceS3Client {
    private static final String BUCKET_NAME = "e-commerce-images-bucket-v1";
    private final S3Client s3Client;


    public ECommerceS3Client(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void testUploadImage(String key) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .build();

        s3Client.putObject(request, RequestBody.fromString("Hello S3!"));
    }
}
