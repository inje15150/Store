package project.shop.web.controller.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderForm {

    @NotEmpty(message = "회원을 선택해주세요.")
    private String memberName;

    @NotEmpty(message = "상품을 선택해주세요.")
    private String itemName;

    @NotNull(message = "금액을 입력해주세요.")
    private int price;
}
