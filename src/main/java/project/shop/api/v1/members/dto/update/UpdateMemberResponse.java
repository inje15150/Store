package project.shop.api.v1.members.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {

    private Long id;

    private String name;
}
