package project.shop.api.v1.items.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ItemDto {

    private String itemName;
    private Integer itemPrice;
    private Integer stockQuantity;
}
