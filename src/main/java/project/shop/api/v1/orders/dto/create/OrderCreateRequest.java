package project.shop.api.v1.orders.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class OrderCreateRequest {

    @NotEmpty(message = "주문하시는 회원을 선택해주세요.")
    private String orderMember;

    @NotEmpty(message = "상품을 선택해주세요.")
    private String itemName;

    @NotNull(message = "주문 수량을 입력해주세요.")
    @Min(value = 1, message = "최소 1개 이상 주문 가능합니다.")
    private Integer count;
}
