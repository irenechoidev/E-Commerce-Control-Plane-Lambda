package ecommerce.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
    private String id;
    private int position;
    private String url;
}
