package project.shop.api.v1.orders.dto.read;

import lombok.Data;
import project.shop.domain.Address;
import project.shop.domain.Order;
import project.shop.domain.OrderItem;
import project.shop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, OrderItem orderItem) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
    }

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        status = order.getStatus();
        address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());

    }
}
