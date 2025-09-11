package ecommerce.api;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetProductImagesResponseDto {
    private List<ImageDto> images;
}
