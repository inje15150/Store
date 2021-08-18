package project.shop.api.v1.members.converter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MemberParameterParsing {

    private String name;
    private String city;

}
