package project.shop.web.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop.domain.Member;
import project.shop.domain.Order;
import project.shop.domain.OrderSearch;
import project.shop.domain.item.Item;
import project.shop.exception.NoEnoughStockException;
import project.shop.service.ItemService;
import project.shop.service.MemberService;
import project.shop.service.OrderService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String createOrderForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        model.addAttribute("orderForm", new OrderForm());

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(//@RequestParam("memberId") Long memberId,
                              //@RequestParam("itemId") Long itemId,
                              //@RequestParam("count") int count,
                              @Validated @ModelAttribute("orderForm") OrderForm orderForm, BindingResult bindingResult, Model model) {
        log.info("createOrder call");

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.findMembers());
            model.addAttribute("items", itemService.findItems());
            return "order/orderForm";
        }

        Item findItem = itemService.findOne(orderForm.getItemId());

        if (findItem.getStockQuantity() < orderForm.getCount()) {
            bindingResult.addError(new ObjectError("orderForm", "주문 수량을 초과하였습니다. 현재 주문 가능 수량은 " + findItem.getStockQuantity() +"개 입니다."));
        }
        if (orderForm.getCount() == 0) {
            bindingResult.addError(new ObjectError("orderForm", "1개 이상 주문을 해야합니다."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.findMembers());
            model.addAttribute("items", itemService.findItems());
            return "order/orderForm";
        }

        orderService.order(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getCount());

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        log.info("orderList Controller call");
        List<Order> orders = orderService.findOrders(orderSearch);


        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {

        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
