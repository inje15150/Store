package project.shop.api.v1.members.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id;
    private String name;
    private String method;


}
