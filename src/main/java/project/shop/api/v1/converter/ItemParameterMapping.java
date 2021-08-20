package project.shop.api.v1.converter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ItemParameterMapping {

    private String itemName;
    @Nullable private Integer price;
    private String sign;

    public ItemParameterMapping(String itemName, String sign) {
        this.itemName = itemName;
        this.sign = sign;
    }
}
