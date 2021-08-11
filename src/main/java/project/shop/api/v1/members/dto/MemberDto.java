package project.shop.api.v1.members.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {

    private String loginId;
    private String name;
}
