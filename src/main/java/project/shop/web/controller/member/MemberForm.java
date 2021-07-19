package project.shop.web.controller.member;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberForm {

    @NotEmpty(message = "이름을 반드시 입력해주세요.")
    private String name;

    @NotEmpty(message = "도시를 반드시 입력해주세요.")
    private String city;

    @NotEmpty(message = "거리를 반드시 입력해주세요.")
    private String street;

    private String zipcode;

}
