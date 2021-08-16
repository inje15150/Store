package project.shop.api.v1.orders.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.shop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderCreateResponse {

    private String memberName;
    private String itemName;
    private int orderCount;
    private OrderStatus orderStatus;
    private LocalDateTime createOrderTime;
}
