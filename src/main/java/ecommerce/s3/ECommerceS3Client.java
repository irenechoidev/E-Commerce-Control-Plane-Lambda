package ecommerce.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

public class ECommerceS3Client {
    private static final String BUCKET_NAME = "e-commerce-images-bucket-v1";
    private static final int EXPIRATION_IN_MINUTES = 15;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public ECommerceS3Client(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public String createPreSignedUrl(String imageKey) {
        String filePath = imageKey + ".jpg";

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(filePath)
                .build();

        PresignedPutObjectRequest preSignedPutRequest = s3Presigner.presignPutObject(
                PutObjectPresignRequest.builder()
                        .putObjectRequest(putRequest)
                        .signatureDuration(Duration.ofMinutes(EXPIRATION_IN_MINUTES))
                        .build()
        );

        return preSignedPutRequest.url().toString();
    }
}
