package project.shop.web.controller.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderForm {

    @NotNull(message = "회원을 선택해주세요.")
    private Long memberId;

    @NotNull(message = "상품을 선택해주세요.")
    private Long itemId;

    @NotNull(message = "수량을 입력해주세요.")
    private int count;

    public OrderForm() {

    }
}
