package ecommerce.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse implements Response {
    @Override
    public Object getData() {
        return new Object();
    }
}
