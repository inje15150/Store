package project.shop.api.v1.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToMemberParameter implements Converter<String, MemberParameterMapping> {

    @Override
    public MemberParameterMapping convert(String source) {
        log.info("convert source= {}", source);

//        query=   name=kim,city=aaa
        String[] params = source.split(",");

        if (params.length == 2) {
            //params[0] = name=kim
            //params[1] = city=aaa
            if (params[0].split("=")[0].equals("name")) {
                String name = params[0].split("=")[1];
                String city = params[1].split("=")[1];
                return new MemberParameterMapping(name, city);
            }
            if (params[0].split("=")[0].equals("city")) {
                String name = params[1].split("=")[1];
                String city = params[0].split("=")[1];
                return new MemberParameterMapping(name, city);
            }
        }

        if (params.length == 1) {
            //params[0] = name=kim, params[1] = city=aaa
            String[] split = params[0].split("=");
            if (split[0].equals("name")) {
                String name = split[1];
                String city = null;
                return new MemberParameterMapping(name, city);
            }
            if (split[0].equals("city")) {
                String name = null;
                String city = split[1];
                return new MemberParameterMapping(name, city);
            }
        }
        return null;
    }
}
