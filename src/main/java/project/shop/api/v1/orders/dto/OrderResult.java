package project.shop.api.v1.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResult<T> {

    private int count;
    private T data;
}
