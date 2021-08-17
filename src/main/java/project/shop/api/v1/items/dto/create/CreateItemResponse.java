package project.shop.api.v1.items.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CreateItemResponse {

    private Long id;
    private String itemName;
    private String method;
    private HttpStatus status;
}
