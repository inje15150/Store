package project.shop.web.controller.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ItemForm {

    private Long itemId;

    @NotEmpty(message = "상품 이름을 입력해주세요.")
    private String itemName;

    @NotNull(message = "가격을 입력해주세요.")
    @Range(min = 1000, max = 1000000, message = "가격은 1,000원 부터 1,000,000원까지 등록 가능합니다.")
    private Integer price;

    @NotNull(message = "상품 재고를 입력해주세요.")
    @Max(value = 9999, message = "상품은 최대 9,999개 까지 등록 가능합니다.")
    private Integer stockQuantity;

    @NotEmpty(message = "상품 종류를 선택해주세요.")
    private String selectItem;

    public ItemForm(Long itemId, String itemName, Integer price, Integer stockQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
