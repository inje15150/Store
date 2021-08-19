package project.shop.api.v1.members.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToMemberParameter implements Converter<String, MemberParameterParsing> {

    @Override
    public MemberParameterParsing convert(String source) {
        log.info("convert source= {}", source);

//        query=   name=kim,city=aaa
        String[] params = source.split(",");

        if (params.length == 2) {
            //params[0] = name=kim
            //params[1] = city=aaa
            String name = params[0].split("=")[1];
            String city = params[1].split("=")[1];

            return new MemberParameterParsing(name, city);
        }

        if (params.length == 1) {
            //params[0] = name=kim, params[1] = city=aaa
            String[] split = params[0].split("=");

            return new MemberParameterParsing(split[0], split[1]);
        }
        return null;
    }
}
