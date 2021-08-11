package project.shop.api.v1.members.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UpdateMemberRequest {

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
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
