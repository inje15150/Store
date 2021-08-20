package project.shop.api.v1.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class StringToItemParameter implements Converter<String, ItemParameterMapping> {

    @Override
    public ItemParameterMapping convert(String query) {

        Map<String, String> parameter = new ConcurrentHashMap<>();
        String itemName;
        Integer price;
        String sign;

        //query=  itemName=item,price<100000
        //params[0] = itemName=item
        //params[1] = price<100000
        String[] params = query.split(",");
        Arrays.sort(params);

        for (String param : params) {
            String[] split = param.split("=");
            if (split.length != 2) {
                sign = String.valueOf(param.charAt(5));
                parameter.put("sign", sign);
                log.info("sign= {}", sign);
            } else {
                parameter.put(split[0], split[1]);
            }
        }

        if (!parameter.containsKey("price")) {
            itemName = parameter.get("itemName");
            sign = parameter.get("sign");
            return new ItemParameterMapping(itemName, sign);
        }

        itemName = parameter.get("itemName");
        price = Integer.parseInt(parameter.get("price"));
        sign = parameter.get("sign");

        return new ItemParameterMapping(itemName, price, sign);
    }
}
