package ecommerce.s3;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

public class ECommerceS3Client {
    private static final String BUCKET_NAME = "e-commerce-images-bucket-v1";
    private static final int PUT_OBJECT_EXPIRATION_MINUTES = 15;
    private static final int GET_OBJECT_EXPIRATION_HOURS = 12;
    private final S3Presigner s3Presigner;

    public ECommerceS3Client(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    public String getPreSignedUrl(String imageKey) {
        String filePath = imageKey + ".jpg";

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(filePath)
                .build();

        PresignedGetObjectRequest preSignedGetRequest = s3Presigner.presignGetObject(
                GetObjectPresignRequest.builder()
                        .getObjectRequest(getRequest)
                        .signatureDuration(Duration.ofHours(GET_OBJECT_EXPIRATION_HOURS))
                        .build()
        );

        return preSignedGetRequest.url().toString();
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
                        .signatureDuration(Duration.ofMinutes(PUT_OBJECT_EXPIRATION_MINUTES))
                        .build()
        );

        return preSignedPutRequest.url().toString();
    }
}
