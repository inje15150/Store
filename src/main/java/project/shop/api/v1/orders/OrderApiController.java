package project.shop.api.v1.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop.api.v1.orders.dto.OrderDto;
import project.shop.api.v1.orders.dto.OrderResult;
import project.shop.domain.Order;
import project.shop.repository.OrderRepository;
import project.shop.repository.order.simplequery.OrderSimpleQueryDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryDto orderSimpleQueryDto;

    @GetMapping("/api/v1/orders")
    public OrderResult<List<OrderDto>> orders() {
        List<Order> orders = orderRepository.findAllMemberDelivery();
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return new OrderResult<>(collect.size(), collect);
    }

    /* *
     *  Entity 직접 조회 X, Dto 를 통해 데이터 조회
     * */
//    @GetMapping("/api/v1/orders")
    public OrderResult<List<OrderDto>> orderDtoToOrders() {
        List<OrderDto> orderDto = orderSimpleQueryDto.findOrderDto();

        return new OrderResult<>(orderDto.size(), orderDto);
    }

}
