package ecommerce.api;

import ecommerce.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponse implements Response {
    private User user;

    @Override
    public Object getData() {
        return user;
    }
}
