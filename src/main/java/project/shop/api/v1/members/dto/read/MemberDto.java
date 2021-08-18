package project.shop.api.v1.members.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.shop.domain.Address;

@Data
@AllArgsConstructor
public class MemberDto {

    private String loginId;
    private String name;
    private Address address;
}
