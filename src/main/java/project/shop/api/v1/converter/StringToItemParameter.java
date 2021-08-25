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

        //query= itemName=itemA
        //params[0] = itemName=itemA
        String[] params = query.split(",");
        Arrays.sort(params);

        for (String param : params) {
            String[] split = param.split("=|<|>|<=|>=");
            //split[0]=itemName, split[1]=itemA

            parameter.put(split[0], split[1]);
        }

        if (parameter.size() != 2) {
            if (!parameter.containsKey("price")) {
                itemName = parameter.get("itemName");
                return new ItemParameterMapping(itemName, null, null);
            }
            if (!parameter.containsKey("itemName")) {
                price = Integer.parseInt(parameter.get("price"));
                sign = String.valueOf(params[0].charAt(5));
                return new ItemParameterMapping(null, price, sign);
            }
        }

        itemName = parameter.get("itemName");
        price = Integer.parseInt(parameter.get("price"));
        sign = String.valueOf(params[1].charAt(5));

        return new ItemParameterMapping(itemName, price, sign);
    }
}
