package project.shop.api.v1.items.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UpdateItemResponse {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer stockQuantity;
    private String method;
    private HttpStatus status;
}
