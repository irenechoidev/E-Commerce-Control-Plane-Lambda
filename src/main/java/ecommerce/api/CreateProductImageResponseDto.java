package ecommerce.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductImageResponseDto {
    private String preSignedUrl;
}
