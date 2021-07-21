package project.shop.web.controller.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름을 반드시 입력해주세요.")
    private String name;

    @NotEmpty(message = "아이디를 반드시 입력해주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String re_password;

    @NotEmpty(message = "도시를 반드시 입력해주세요.")
    private String city;

    @NotEmpty(message = "거리를 반드시 입력해주세요.")
    private String street;

    @NotEmpty(message = "거리를 반드시 입력해주세요.")
    private String zipcode;

}
