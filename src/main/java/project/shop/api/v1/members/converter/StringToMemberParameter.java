package project.shop.api.v1.members.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToMemberParameter implements Converter<String, MemberParameterParsing> {

    @Override
    public MemberParameterParsing convert(String source) {
        log.info("convert source= {}", source);
        String name = null;
        String city = null;

        //name %3D kim & %3D city=aaa
        String[] params = source.split("&");


        if (params.length == 2) {
            name = params[0].split("%3D")[1];
            city = params[1].split("%3D")[1];
        }
        if (params.length == 1) {
            if (params[0].equals("name")) {
                name = params[0].split("%3D")[1];
            }
            if (params[0].equals("city")) {
                city = params[0].split("%3D")[1];
            }
        }
        return new MemberParameterParsing(name, city);
    }
}
