package project.shop.api.v1.converter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ItemParameterMapping {

    private final String itemName;
    @Nullable private Integer price;
    private final String sign;

    public ItemParameterMapping(String itemName, String sign) {
        this.itemName = itemName;
        this.sign = sign;
    }
}
