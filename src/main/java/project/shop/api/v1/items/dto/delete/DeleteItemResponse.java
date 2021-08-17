package project.shop.api.v1.items.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class DeleteItemResponse {

    private String requestURI;
    private String method;
    private HttpStatus status;
}
