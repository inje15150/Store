package project.shop.api.v1.items.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateItemRequest {

    @NotEmpty(message = "상품 종류를 선택해주세요.")
    private String itemType;

    @NotEmpty(message = "상품 이름을 입력해주세요.")
    private String itemName;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @Range(min = 1000, max = 1000000, message = "가격은 1,000 ~ 1,000,000원 까지 등록 가능합니다.")
    private Integer price;

    @NotNull(message = "상품 재고 수량을 입력해주세요.")
    @Max(value = 9999, message = "상품 재고는 9,999개 까지 등록 가능합니다.")
    private Integer stockQuantity;
}
