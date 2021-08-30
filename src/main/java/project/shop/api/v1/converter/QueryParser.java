package project.shop.api.v1.converter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import project.shop.domain.Address;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Setter
public class QueryParser<T>{

    public T parse(String query, T t) {

        Class<T> clazz = (Class<T>) t.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] params = query.split(",");
        // params[0] = name=kim
        // params[1] = city=aaa

        for (String param : params) {
            String[] split = param.split("=");
            //split[0]=name, split[1]=kim
            //split[0]=city, split[1]=aaa

            for (Field field : fields) {
                if (split[0].equals(field.getName())) {
                    try {
                        field.setAccessible(true);
                        if (field.getName().equals("price")) {
                            field.set(t, Integer.valueOf(split[1]));
                        } else {
                            field.set(t, split[1]);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return t;
    }
}
