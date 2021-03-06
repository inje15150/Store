package project.shop.api.v1.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.api.v1.Errors;
import project.shop.api.v1.converter.OrderParameterMapping;
import project.shop.api.v1.converter.QueryParser;
import project.shop.api.v1.orders.dto.cancel.OrderCancelResponse;
import project.shop.api.v1.orders.dto.create.OrderCreateRequest;
import project.shop.api.v1.orders.dto.create.OrderCreateResponse;
import project.shop.api.v1.orders.dto.read.OrderDto;
import project.shop.api.v1.orders.dto.read.OrderResult;
import project.shop.domain.Member;
import project.shop.domain.Order;
import project.shop.domain.OrderStatus;
import project.shop.domain.item.Item;
import project.shop.repository.OrderRepository;
import project.shop.repository.order.simplequery.OrderSimpleQueryDto;
import project.shop.repository.springdatajpa.SpringJpaOrderRepository;
import project.shop.service.ItemService;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderSimpleQueryDto orderSimpleQueryDto;
    private final ItemService itemService;
    private final MemberService memberService;
    private final SpringJpaOrderRepository jpaOrderRepository;

    /*
     * 상품 목록 API
     * */
    @GetMapping("/api/v1/orders")
    public ResponseEntity orders(@Nullable @RequestParam("query") String query) {

        if (query == null) {
            List<Order> orders = jpaOrderRepository.findOrders();
            List<OrderDto> collect = orderDtoChange(orders);
            return new ResponseEntity(new OrderResult(collect.size(), collect), HttpStatus.OK);
        }
//        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        //

        QueryParser<OrderParameterMapping> q = new QueryParser<>();
        OrderParameterMapping parse = q.parse(query, new OrderParameterMapping());

        String username = parse.getUsername();
        String status = parse.getStatus();
        List<Order> orders = new ArrayList<>();

        if (username == null) {
            orders = jpaOrderRepository.findByStatus(status);
        } else if (status == null) {
            orders = jpaOrderRepository.findByName(username);
        } else {
            orders = jpaOrderRepository.findByUsernameAndStatus(username, status);
        }

        List<OrderDto> collect = orderDtoChange(orders);

        return new ResponseEntity(new OrderResult(collect.size(), collect), HttpStatus.OK);
    }

    private List<OrderDto> orderDtoChange(List<Order> orders) {
        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
    }

    /* *
     *  Entity 직접 조회 X, Dto 를 통해 데이터 조회
     * */
//    @GetMapping("/api/v1/orders")
    public OrderResult<List<OrderDto>> orderDtoToOrders() {
        List<OrderDto> orderDto = orderSimpleQueryDto.findOrderDto();

        return new OrderResult<>(orderDto.size(), orderDto);
    }

    /*
     * 상품 주문 API
     * */
    @PostMapping("/api/v1/orders/create")
    public ResponseEntity order(@RequestBody @Validated OrderCreateRequest param, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity(new Errors(errorList), HttpStatus.BAD_REQUEST);
        }

        Long itemId = itemService.findByName(param.getItemName());
        Long memberId = memberService.findByName(param.getOrderMember());

        Item findItem = itemService.findOne(itemId);
        Member findMember = memberService.findOne(memberId);

        if (findItem.getStockQuantity() < param.getCount()) {
            bindingResult.addError(new FieldError("orderCreateRequest", "count", "상품 재고 수량을 초과했습니다. 현재 재고 수량 " + findItem.getStockQuantity() + "개"));
        }

        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity(new Errors(errorList), HttpStatus.BAD_REQUEST);
        }

        Long orderId = orderService.order(memberId, itemId, param.getCount());
        Order findOrder = orderRepository.findOne(orderId);

        return new ResponseEntity(
                new OrderCreateResponse(findMember.getName(), findItem.getName(), param.getCount(), OrderStatus.ORDER, findOrder.getOrderDate()), HttpStatus.OK);
    }

    /*
     * 주문 취소 API
     * */
    @DeleteMapping("/api/v1/orders/{orderId}/cancel")
    public ResponseEntity orderCancel(@PathVariable("orderId") Long orderId, HttpServletRequest request) {

        orderService.cancelOrder(orderId);

        return new ResponseEntity(new OrderCancelResponse(request.getRequestURI(), request.getMethod(), HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);

    }

}
