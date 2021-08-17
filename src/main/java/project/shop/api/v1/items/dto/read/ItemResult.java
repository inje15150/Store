package project.shop.api.v1.items.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemResult<T> {

    private int count;
    private T items;
}
